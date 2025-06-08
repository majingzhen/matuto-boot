package com.matuto.boot.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.matuto.boot.common.utils.SecurityUtils;
import com.matuto.boot.system.entity.SysMenu;
import com.matuto.boot.system.entity.SysRoleMenu;
import com.matuto.boot.system.mapper.SysMenuMapper;
import com.matuto.boot.system.mapper.SysRoleMenuMapper;
import com.matuto.boot.system.service.SysMenuService;
import com.matuto.boot.common.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysRoleMenuMapper roleMenuMapper;

    @Override
    public Page<SysMenu> getMenuList(SysMenu menu, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotEmpty(menu.getMenuName()), SysMenu::getMenuName, menu.getMenuName())
                .eq(StrUtil.isNotEmpty(menu.getStatus()), SysMenu::getStatus, menu.getStatus())
                .orderByAsc(SysMenu::getOrderNum);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    public List<SysMenu> getMenuTree() {
        List<SysMenu> menus = list(new LambdaQueryWrapper<SysMenu>()
                .orderByAsc(SysMenu::getOrderNum));
        return buildMenuTree(menus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMenu(SysMenu menu) {
        checkMenuParams(menu);
        save(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(SysMenu menu) {
        checkMenuParams(menu);
        updateById(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Long menuId) {
        if (hasChildByMenuId(menuId)) {
            throw new ServiceException("存在子菜单,不允许删除");
        }
        if (checkMenuExistRole(menuId)) {
            throw new ServiceException("菜单已分配,不允许删除");
        }
        removeById(menuId);
    }

    @Override
    public List<SysMenu> getMenuTreeByUserId(Long userId) {
        List<SysMenu> menus;
        if (SecurityUtils.isAdmin(userId)) {
            menus = list();
        } else {
            menus = baseMapper.selectMenusByUserId(userId);
        }
        return buildMenuTree(menus);
    }

    @Override
    public Set<String> getMenuPermsByUserId(Long userId) {
        List<String> perms;
        if (SecurityUtils.isAdmin(userId)) {
            perms = list().stream()
                    .map(SysMenu::getPerms)
                    .filter(StrUtil::isNotEmpty)
                    .collect(Collectors.toList());
        } else {
            perms = baseMapper.selectMenuPermsByUserId(userId);
        }
        return new HashSet<>(perms);
    }

    @Override
    public List<SysMenu> getMenuListByRoleId(Long roleId) {
        return baseMapper.selectMenusByRoleId(roleId);
    }

    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        List<SysMenu> returnList = new ArrayList<>();
        List<Long> tempList = menus.stream().map(SysMenu::getMenuId).collect(Collectors.toList());
        for (SysMenu menu : menus) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId())) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    @Override
    public boolean hasChildByMenuId(Long menuId) {
        return count(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getParentId, menuId)) > 0;
    }

    @Override
    public boolean checkMenuExistRole(Long menuId) {
        return roleMenuMapper.selectCount(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getMenuId, menuId)) > 0;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        return list.stream()
                .filter(n -> n.getParentId().equals(t.getMenuId()))
                .collect(Collectors.toList());
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return !getChildList(list, t).isEmpty();
    }

    /**
     * 检查菜单参数
     */
    private void checkMenuParams(SysMenu menu) {
        if (StrUtil.isEmpty(menu.getMenuName())) {
            throw new ServiceException("菜单名称不能为空");
        }
        if (menu.getOrderNum() == null) {
            throw new ServiceException("菜单顺序不能为空");
        }
        if (StrUtil.isEmpty(menu.getPath())) {
            throw new ServiceException("路由地址不能为空");
        }
    }
} 