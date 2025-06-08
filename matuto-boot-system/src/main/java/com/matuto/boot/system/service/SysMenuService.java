package com.matuto.boot.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.matuto.boot.system.entity.SysMenu;

import java.util.List;
import java.util.Set;

/**
 * 菜单服务接口
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 获取菜单列表
     *
     * @param menu     菜单信息
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 菜单列表
     */
    Page<SysMenu> getMenuList(SysMenu menu, Integer pageNum, Integer pageSize);

    /**
     * 获取菜单树
     *
     * @return 菜单树
     */
    List<SysMenu> getMenuTree();

    /**
     * 新增菜单
     *
     * @param menu 菜单信息
     */
    void addMenu(SysMenu menu);

    /**
     * 修改菜单
     *
     * @param menu 菜单信息
     */
    void updateMenu(SysMenu menu);

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     */
    void deleteMenu(Long menuId);

    /**
     * 获取用户菜单树
     *
     * @param userId 用户ID
     * @return 菜单树
     */
    List<SysMenu> getMenuTreeByUserId(Long userId);

    /**
     * 获取用户权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> getMenuPermsByUserId(Long userId);

    /**
     * 获取角色菜单
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<SysMenu> getMenuListByRoleId(Long roleId);

    /**
     * 构建菜单树
     *
     * @param menus 菜单列表
     * @return 菜单树
     */
    List<SysMenu> buildMenuTree(List<SysMenu> menus);

    /**
     * 检查是否存在子菜单
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    boolean hasChildByMenuId(Long menuId);

    /**
     * 检查菜单是否被角色使用
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    boolean checkMenuExistRole(Long menuId);
} 