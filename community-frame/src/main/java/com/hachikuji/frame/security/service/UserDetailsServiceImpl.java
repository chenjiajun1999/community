package com.hachikuji.frame.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hachikuji.core.utils.StringUtils;
import com.hachikuji.frame.mapper.UserMapper;
import com.hachikuji.frame.security.model.LoginUser;
import com.hachikuji.core.exception.ServiceException;
import com.hachikuji.frame.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户验证处理
 *
 * @author ruoyi
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);

        if (StringUtils.isNull(user))
        {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("登录用户：" + username + " 不存在");
        }

        return createLoginUser(user);
    }

    /**
     * 先加一个默认最高权限给登录的用户
     */
    public UserDetails createLoginUser(User user)
    {

        Set<String> permissions = new HashSet<>();
        permissions.add("*:*:*");

        return new LoginUser(user.getId(),  user, permissions);
    }
}
