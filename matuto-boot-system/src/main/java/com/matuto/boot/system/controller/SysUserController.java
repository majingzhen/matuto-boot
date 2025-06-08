package com.matuto.boot.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.matuto.boot.common.domain.AjaxResult;
import com.matuto.boot.system.entity.SysUser;
import com.matuto.boot.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService userService;

    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    @SaCheckPermission("system:user:list")
    public AjaxResult list(SysUser user,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return AjaxResult.success(userService.getUserList(user, pageNum, pageSize));
    }

    @Operation(summary = "新增用户")
    @PostMapping
    @SaCheckPermission("system:user:add")
    public AjaxResult add(@RequestBody SysUser user) {
        userService.addUser(user);
        return AjaxResult.success();
    }

    @Operation(summary = "修改用户")
    @PutMapping
    @SaCheckPermission("system:user:edit")
    public AjaxResult update(@RequestBody SysUser user) {
        userService.updateUser(user);
        return AjaxResult.success();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{userId}")
    @SaCheckPermission("system:user:remove")
    public AjaxResult remove(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return AjaxResult.success();
    }

    @Operation(summary = "获取用户角色")
    @GetMapping("/role/{userId}")
    @SaCheckPermission("system:user:query")
    public AjaxResult getUserRoles(@PathVariable Long userId) {
        return AjaxResult.success(userService.getUserRoles(userId));
    }

    @Operation(summary = "更新用户角色")
    @PutMapping("/role/{userId}")
    @SaCheckPermission("system:user:edit")
    public AjaxResult updateUserRoles(@PathVariable Long userId, @RequestBody List<Long> roleIds) {
        userService.updateUserRoles(userId, roleIds);
        return AjaxResult.success();
    }
} 