package com.matuto.boot.system.controller;

import com.matuto.boot.common.domain.AjaxResult;
import com.matuto.boot.system.domain.vo.LoginVO;
import com.matuto.boot.system.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginVO loginVO) {
        String token = authService.login(loginVO);
        return AjaxResult.success(token);
    }
    
    @PostMapping("/logout")
    public AjaxResult logout() {
        authService.logout();
        return AjaxResult.success();
    }
    
    @GetMapping("/user-info")
    public AjaxResult getUserInfo() {
        return AjaxResult.success(authService.getUserInfo());
    }
} 