package com.matuto.boot.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.matuto.boot.common.domain.AjaxResult;
import com.matuto.boot.common.utils.SecurityUtils;
import com.matuto.boot.system.entity.SysMenu;
import com.matuto.boot.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "菜单管理")
@RestController
@RequestMapping("/system/menu")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService menuService;

    @Operation(summary = "获取菜单列表")
    @GetMapping("/list")
    @SaCheckPermission("system:menu:list")
    public AjaxResult list(SysMenu menu,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return AjaxResult.success(menuService.getMenuList(menu, pageNum, pageSize));
    }

    @Operation(summary = "获取菜单树")
    @GetMapping("/tree")
    @SaCheckPermission("system:menu:list")
    public AjaxResult tree() {
        return AjaxResult.success(menuService.getMenuTree());
    }

    @Operation(summary = "新增菜单")
    @PostMapping
    @SaCheckPermission("system:menu:add")
    public AjaxResult add(@RequestBody SysMenu menu) {
        menuService.addMenu(menu);
        return AjaxResult.success();
    }

    @Operation(summary = "修改菜单")
    @PutMapping
    @SaCheckPermission("system:menu:edit")
    public AjaxResult update(@RequestBody SysMenu menu) {
        menuService.updateMenu(menu);
        return AjaxResult.success();
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/{menuId}")
    @SaCheckPermission("system:menu:remove")
    public AjaxResult remove(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return AjaxResult.success();
    }

    @Operation(summary = "获取用户菜单树")
    @GetMapping("/user-menus")
    public AjaxResult getUserMenuTree() {
        return AjaxResult.success(menuService.getMenuTreeByUserId(SecurityUtils.getLoginUserId()));
    }
} 