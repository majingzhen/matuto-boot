package com.matuto.boot.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.matuto.boot.common.domain.AjaxResult;
import com.matuto.boot.system.entity.SysRole;
import com.matuto.boot.system.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "角色管理")
@RestController
@RequestMapping("/system/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService roleService;

    @Operation(summary = "获取角色列表")
    @GetMapping("/list")
    @SaCheckPermission("system:role:list")
    public AjaxResult list(SysRole role,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return AjaxResult.success(roleService.getRoleList(role, pageNum, pageSize));
    }

    @Operation(summary = "获取所有角色")
    @GetMapping("/all")
    @SaCheckPermission("system:role:list")
    public AjaxResult all() {
        return AjaxResult.success(roleService.getAllRoles());
    }

    @Operation(summary = "新增角色")
    @PostMapping
    @SaCheckPermission("system:role:add")
    public AjaxResult add(@RequestBody SysRole role) {
        roleService.addRole(role);
        return AjaxResult.success();
    }

    @Operation(summary = "修改角色")
    @PutMapping
    @SaCheckPermission("system:role:edit")
    public AjaxResult update(@RequestBody SysRole role) {
        roleService.updateRole(role);
        return AjaxResult.success();
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{roleId}")
    @SaCheckPermission("system:role:remove")
    public AjaxResult remove(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return AjaxResult.success();
    }

    @Operation(summary = "获取角色菜单")
    @GetMapping("/menu/{roleId}")
    @SaCheckPermission("system:role:query")
    public AjaxResult getRoleMenus(@PathVariable Long roleId) {
        return AjaxResult.success(roleService.getRoleMenus(roleId));
    }

    @Operation(summary = "更新角色菜单")
    @PutMapping("/menu/{roleId}")
    @SaCheckPermission("system:role:edit")
    public AjaxResult updateRoleMenus(@PathVariable Long roleId, @RequestBody List<Long> menuIds) {
        roleService.updateRoleMenus(roleId, menuIds);
        return AjaxResult.success();
    }
} 