package com.matuto.boot.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.matuto.boot.common.constant.UserConstants;
import com.matuto.boot.common.domain.LoginUser;
import com.matuto.boot.common.exception.ServiceException;
import com.matuto.boot.common.utils.PasswordUtils;
import com.matuto.boot.common.utils.SecurityUtils;
import com.matuto.boot.system.domain.vo.LoginUserPerVO;
import com.matuto.boot.system.domain.vo.LoginUserVO;
import com.matuto.boot.system.domain.vo.LoginVO;
import com.matuto.boot.system.entity.SysUser;
import com.matuto.boot.system.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${captcha.open}")
    private boolean openCaptcha;

    private final SysUserService userService;
    
    private final CaptchaService captchaService;

    private final SysRoleService roleService;

    private final SysMenuService menuService;
    
    @Override
    public String login(LoginVO loginVO) {

        if (openCaptcha) {
            // 验证码校验
            captchaService.validate(loginVO.getUuid(), loginVO.getCaptcha());
        }

        // 用户验证
        SysUser user = userService.selectUserByUserName(loginVO.getUsername());
        if (user == null) {
            throw new ServiceException("登录用户不存在");
        }
        if (UserConstants.USER_DISABLE.equals(user.getStatus())) {
            throw new ServiceException("用户已停用，请联系管理员");
        }
        // 密码验证
        if (!PasswordUtils.verify(loginVO.getPassword(), user.getSalt(), user.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        
        // 生成token
        StpUtil.login(user.getId(), loginVO.getRememberMe() ? 7 * 24 * 60 * 60 : 2 * 60 * 60);

        // 设置用户信息
        // 生成token
        StpUtil.login(user.getId());
        // 设置用户信息
        LoginUser loginUser = new LoginUser()
                .setId(user.getId())
                .setToken(StpUtil.getTokenValue())
                .setLoginTime(System.currentTimeMillis())
                .setExpireTime(StpUtil.getTokenTimeout() * 1000)
                .setUser(user)
                .setUserType(user.getUserType());

        // 保存用户信息
        StpUtil.getSession().set("loginUser", loginUser);
        return StpUtil.getTokenValue();
    }
    
    @Override
    public void logout() {
        StpUtil.logout();
    }
    
    @Override
    public LoginUserVO getUserInfo() {
        SysUser sysUser = userService.getById(StpUtil.getLoginIdAsLong());
        if (sysUser == null) {
            throw new ServiceException("用户不存在");
        }
        return LoginUserVO.build(sysUser);
    }

    @Override
    public LoginUserPerVO getUserPerms() {
        LoginUserPerVO loginUserPerVO = new LoginUserPerVO();
        loginUserPerVO.setRole(roleService.selectRoleCodeByUserId(SecurityUtils.getLoginUserId()));
        loginUserPerVO.setPermission(menuService.getMenuPermsByUserId(SecurityUtils.getLoginUserId()));
        loginUserPerVO.setMenu(menuService.getMenuTreeByUserId(SecurityUtils.getLoginUserId()));
        return loginUserPerVO;
    }
} 