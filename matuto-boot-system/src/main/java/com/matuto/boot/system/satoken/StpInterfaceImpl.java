package com.matuto.boot.system.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.matuto.boot.system.service.AuthService;
import com.matuto.boot.system.service.SysMenuService;
import com.matuto.boot.system.service.SysRoleService;
import com.matuto.boot.system.service.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private SysMenuService menuService;

    @Resource
    private SysRoleService roleService;

    // 返回指定账号id拥有的权限码集合
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // loginId 通常就是用户ID
        // 这里调用你的业务方法查权限
        Set<String> menuPermsByUserId = menuService.getMenuPermsByUserId(Long.parseLong(loginId.toString()));
        return menuPermsByUserId.stream().toList();
    }

    // 返回指定账号id拥有的角色标识集合
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Set<String> roleCodes = roleService.selectRoleCodeByUserId(Long.parseLong(loginId.toString()));
        return roleCodes.stream().toList();
    }
}