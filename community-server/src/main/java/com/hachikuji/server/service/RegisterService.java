package com.hachikuji.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hachikuji.core.exception.ServiceException;
import com.hachikuji.server.utils.EmailClient;
import com.hachikuji.frame.mapper.UserMapper;
import com.hachikuji.frame.pojo.User;
import com.hachikuji.core.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class RegisterService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailClient emailClient;


    /**
     * 未激活
     */
    public static int ACTIVATION_UNDO = 0;
    /**
     * 已激活
     */
    public static int ACTIVATION_DONE = 1;

    /**
     * 重复激活
     */
    public static int ACTIVATION_REPEAT = 2;
    private static final boolean shouldActivate = false;

    public boolean exist(String key, String value) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(key, value);
        User user = userMapper.selectOne(queryWrapper);
        return user != null;

    }

    public int register(String username, String password, String email) {

        User user = createUser(username, password, email);

        // 方便测试
        if (!shouldActivate)
            return user.getId();


        // 发送邮件
        String link = "http://localhost:3000/" + user.getActivationCode() + "/activation/" + user.getId();
        String content = "<html>\n" +
                "<body>\n" +
                "    <h3>" + email + "，您好！</h3>\n" +
                "    <h3>您正在注册牛客网，这是一封激活邮件，请点击 <a href=\"" + link + "\">此链接</a> ，激活您的牛客账号！</h3>\n" +
                "</body>\n" +
                "</html>";
        try {
            emailClient.sendEmail(email, "激活账号", content);
        } catch (MessagingException e) {
            throw new ServiceException(e.getMessage());
        }
        return user.getId();
    }

    public User createUser(String username, String password, String email) {

        String randomSalt = IdUtils.simpleUUID().substring(0, 5);
        User user = new User();

        user.setUsername(username);
        user.setSalt(randomSalt);
        user.setPassword(DigestUtils.md5DigestAsHex((randomSalt + password).getBytes()));
        user.setEmail(email);

        // 方便测试
        // user.setStatus(0);
        user.setStatus(shouldActivate ? ACTIVATION_UNDO : ACTIVATION_DONE);

        user.setType(0);
        user.setActivationCode(IdUtils.simpleUUID());
        user.setHeaderUrl("http://images.nowcoder.com/head/" + new Random().nextInt(1000) + "t.png");
        user.setCreateTime(LocalDateTime.now());

        userMapper.insert(user);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        user = userMapper.selectOne(queryWrapper);

        return user;

    }

    public String activate(int id, String activationCode) {

        User user = userMapper.selectById(id);

        if (user.getStatus() == ACTIVATION_DONE)

            return "该账号已经被激活，请不要重复激活！";

        if (user.getActivationCode().equals(activationCode)) {

            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id).set("status", ACTIVATION_DONE);
            userMapper.update(null, updateWrapper);

            return "激活成功，您的账号已经可以正常使用了！";

        }
        return "激活失败，请联系管理员！";

    }

    public String status(int id) {

        return userMapper.selectById(id).getStatus() == ACTIVATION_UNDO ?
                "注册成功，我们已经向您的邮箱发送了一封激活邮件，请尽快激活！" :
                "该账号已经被激活，请登录！";

    }


}
