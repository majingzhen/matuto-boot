package com.matuto.boot.system.domain.vo;

import com.matuto.boot.common.domain.LoginUser;
import com.matuto.boot.system.entity.SysMenu;
import com.matuto.boot.system.entity.SysRole;
import com.matuto.boot.system.entity.SysUser;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @ClassName LoginUserVO
 * @Description 登录对象
 * @Author Majz
 * @Date 2025/6/9 07:36
 */
@Data
public class LoginUserVO {

    private Long userId;

    private String userName;

    private String nickName;

    private String avatar;

    private String email;

    private String phone;

    private String gender;

    private String status;

    private String userType;

    private List<SysRole>  roles;

    private List<SysMenu> menus;

    private Set<String> permissions;

    public static LoginUserVO build(SysUser sysUser, List<SysRole> roles, List<SysMenu> menus, Set<String> permissions) {
        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setUserId(sysUser.getId());
        loginUserVO.setUserName(sysUser.getUserName());
        loginUserVO.setNickName(sysUser.getNickName());
        loginUserVO.setAvatar(sysUser.getAvatar());
        loginUserVO.setEmail(sysUser.getEmail());
        loginUserVO.setPhone(sysUser.getPhone());
        loginUserVO.setGender(sysUser.getGender());
        loginUserVO.setStatus(sysUser.getStatus());
        loginUserVO.setUserType(sysUser.getUserType());
        loginUserVO.setRoles(roles);
        loginUserVO.setMenus(menus);
        loginUserVO.setPermissions(permissions);
        return loginUserVO;
    }
}
