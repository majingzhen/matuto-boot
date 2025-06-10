package com.matuto.boot.system.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import com.matuto.boot.common.domain.AjaxResult;
import com.matuto.boot.system.domain.vo.LoginVO;
import com.matuto.boot.system.entity.SysUser;
import com.matuto.boot.system.service.AuthService;
import com.matuto.boot.system.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    private final LoginService loginService;


    @SaIgnore
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginVO loginVO) {
        String token = authService.login(loginVO);
        return AjaxResult.success("登录成功",token);
    }

    @SaIgnore
    @PostMapping("/logout")
    public AjaxResult logout() {
        authService.logout();
        return AjaxResult.success();
    }


    @SaCheckLogin
    @GetMapping("/user-info")
    public AjaxResult getUserInfo() {
        return AjaxResult.success(authService.getUserInfo());
    }
} 