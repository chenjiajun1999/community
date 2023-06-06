package com.hachikuji.server.controller;

import com.hachikuji.core.constant.Constants;
import com.hachikuji.core.domain.AjaxResult;
import com.hachikuji.server.models.dto.LoginDTO;
import com.hachikuji.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录接口
     * 通过 SpringSecurity 的 token 授权机制控制访问权限
     * /logout 登出接口在其配置类中设置
     *
     * @param body 登录信息
     * @return token
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginDTO body){

        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(body.getUsername(),body.getPassword(), body.getCode(), body.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }




}
