package com.hachikuji.server.service;

import com.hachikuji.frame.mapper.UserMapper;
import com.hachikuji.frame.security.utils.SecurityUtils;
import com.hachikuji.server.models.vo.UserVO;
import com.hachikuji.frame.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 无参数默认放回本用户的信息
     * @return 用户 DO
     */

    public UserVO getLoginUser() {

        User user = SecurityUtils.getLoginUser().getUser();
        return new UserVO(user.getUsername(), user.getHeaderUrl());
    }


    public UserVO getUserById(Integer id) {

        User user = userMapper.selectById(id);
        return new UserVO(user.getUsername(), user.getHeaderUrl());
    }

}
