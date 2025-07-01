package com.matuto.boot.system.domain.vo;

import com.matuto.boot.system.entity.SysMenu;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @ClassName UserPermissionsVO
 * @Description TODO
 * @Author Majz
 * @Date 2025/6/30 16:34
 */
@Data
public class LoginUserPerVO {

    /**
     * 角色
     */
    private Set<String> role;

    /**
     * 权限
     */
    private Set<String> permission;

    /**
     * 菜单
     */
    private List<SysMenu> menu;
}
