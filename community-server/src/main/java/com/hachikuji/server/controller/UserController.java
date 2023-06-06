package com.hachikuji.server.controller;

import com.hachikuji.core.domain.AjaxResult;
import com.hachikuji.server.service.UserService;
import com.hachikuji.server.models.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     *
     * @return 用户信息
     */
    @GetMapping("")
    public AjaxResult getLoginUser(){

        UserVO user = userService.getLoginUser();
        return AjaxResult.success(user);

    }

    @GetMapping("/{id}")
    public AjaxResult getByUserId(@PathVariable Integer id){

        UserVO user = userService.getUserById(id);
        return AjaxResult.success(user);

    }
}
