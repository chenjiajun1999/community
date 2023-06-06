package com.hachikuji.server.controller;

import com.hachikuji.core.domain.AjaxResult;
import com.hachikuji.server.models.dto.RegisterDTO;
import com.hachikuji.server.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 注册控制器
 *
 * @author hachikuji
 * @since 2021-11-29
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     * 注册账户
     *
     * @param registerDTO 注册信息
     * @return 注册得到 id
     */
    @PostMapping("")
    public AjaxResult register(@RequestBody RegisterDTO registerDTO) {

        int id = registerService.register(registerDTO.getUsername(), registerDTO.getPassword(), registerDTO.getEmail());
        return AjaxResult.success(id);

    }

    /**
     * 判断邮箱是否已经存在
     *
     * @return true or false
     */
    @GetMapping("/email/{email}/status")
    public AjaxResult getExistByEmail(@PathVariable String email) {

        boolean isExist = registerService.exist("email", email);
        return AjaxResult.success(isExist);

    }


    /**
     * 判断账号是否已经存在
     *
     * @return true or false
     */
    @GetMapping("/username/{username}/status")
    public AjaxResult getExistByUsername(@PathVariable String username) {

        boolean isExist = registerService.exist("username", username);
        return AjaxResult.success(isExist);

    }


    /**
     * 查看账户的激活状态
     *
     * @param userId 用户 id
     * @return 状态信息
     */
    @GetMapping("/{userId}/status")
    public AjaxResult getStatusByUserId(@PathVariable int userId) {

        String status = registerService.status(userId);
        return AjaxResult.success(status);
    }

    /**
     * 激活账户
     *
     * @param userId         用户 userId
     * @param activateCode   用户 激活码
     * @return 激活反馈信息
     */
    @PostMapping("/{activateCode}/activation/{userId}")
    public AjaxResult activate(@PathVariable int userId, @PathVariable String activateCode) {

        String message = registerService.activate(userId, activateCode);
        return AjaxResult.success(message);

    }

}
