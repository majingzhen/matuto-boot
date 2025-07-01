package com.matuto.boot.system.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import com.matuto.boot.common.domain.AjaxResult;
import com.matuto.boot.system.domain.vo.LoginVO;
import com.matuto.boot.system.entity.SysUser;
import com.matuto.boot.system.service.AuthService;
import com.matuto.boot.system.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "认证控制器")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @SaIgnore
    @Operation(summary = "登录")
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginVO loginVO) {
        String token = authService.login(loginVO);
        return AjaxResult.success("登录成功",token);
    }

    @SaIgnore
    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public AjaxResult logout() {
        authService.logout();
        return AjaxResult.success();
    }


    @SaCheckLogin
    @Operation(summary = "获取用户信息")
    @GetMapping("/user-info")
    public AjaxResult getUserInfo() {
        return AjaxResult.success(authService.getUserInfo());
    }

    @SaCheckLogin
    @Operation(summary = "获取用户权限")
    @GetMapping("/user-perms")
    public AjaxResult getUserPerms() {
        return AjaxResult.success(authService.getUserPerms());
    }
} 