package com.hachikuji.server.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hachikuji.core.domain.model.DataTable;
import com.hachikuji.core.service.BaseService;
import com.hachikuji.frame.mapper.MessageMapper;
import com.hachikuji.frame.mapper.UserMapper;
import com.hachikuji.frame.pojo.Message;
import com.hachikuji.frame.pojo.User;
import com.hachikuji.frame.security.utils.SecurityUtils;
import com.hachikuji.server.models.vo.MessageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MessageService extends BaseService {


    @Resource
    MessageMapper messageMapper;

    @Resource
    UserMapper userMapper;

//    @Autowired
//    UserService userService;

    /**
     * 私信类型
     */
    public static int MESSAGE_UNREAD = 0;
    public static int MESSAGE_READ = 1;

    /**
     * 分页查询当前用户的会话列表，针对每个会话放回最新一条私信
     * total 所有会话数量
     *
     * @return 未读数和 notice 数
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, Object> pageChats() {

        startPage();
        Integer userId = SecurityUtils.getUserId();
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("conversation_id", userId)
                .groupBy("conversation_id")  // should fix 'ONLY_FULL_GROUP_BY'
                .orderByDesc("create_time");

        List<Message> messages = messageMapper.selectList(queryWrapper);
        long total = getDataTable(messages).getTotal();

        List<Object> chats = new ArrayList<>();
        int totalUnreadCount = 0;

        for (Message m : messages) {

            MessageVO vo = appendUserInfo(m, userId);

            queryWrapper.clear();
            queryWrapper.eq("conversation_id", m.getConversationId())
                    .eq("status", MESSAGE_UNREAD);
            int count = messageMapper.selectCount(queryWrapper);
            totalUnreadCount += count;

            vo.setUnreadCount(count);
            chats.add(vo);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("data", totalUnreadCount);
        result.put("rows", chats);
        result.put("total", total);
        return result;
    }

    /**
     * 分页查询某个会话的所有私信
     * total 所有私信数量
     *
     * @return list
     */
    public DataTable pageByToId(Integer toId) {

        startPage();

        Integer userId = SecurityUtils.getUserId();
        String conversationId = softConversationId(userId, toId);
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("conversation_id", conversationId)
                .orderByDesc("create_time");

        List<Message> messages = messageMapper.selectList(queryWrapper);
        long total = getDataTable(messages).getTotal();


        return getDataTable(messages, total);
    }

    public void add(Integer toId, String content) {

        Integer userId = SecurityUtils.getUserId();

        Message message = Message.builder()
                .fromId(userId)
                .toId(toId)
                .status(MESSAGE_UNREAD)
                .conversationId(softConversationId(userId, toId))
                .content(content)
                .createTime(LocalDateTime.now()).build();

        messageMapper.insert(message);

    }

    public void updateStateByToId(Integer toId) {

        Integer userId = SecurityUtils.getUserId();

        UpdateWrapper<Message> wrapper = new UpdateWrapper<>();
        wrapper.eq("conversation_id", softConversationId(userId, toId))
                .set("state", MESSAGE_READ);

        messageMapper.update(null, wrapper);
    }

    private String softConversationId(Integer id1, Integer id2) {
        if (id1 < id2)
            return id1 + "_" + id2;
        else
            return id2 + "_" + id1;
    }

    private MessageVO appendUserInfo(Message m, int userId) {

        MessageVO vo = new MessageVO();
        BeanUtils.copyProperties(m, vo);

        User u = userMapper.selectById(Objects.equals(userId, m.getToId()) ? m.getFromId() : m.getToId());
        vo.setUsername(u.getUsername());
        vo.setHeaderUrl(u.getHeaderUrl());
        vo.setUserId(u.getId());
        return vo;

    }


}
