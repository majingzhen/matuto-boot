package com.matuto.boot.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.matuto.boot.common.constant.UserConstants;
import com.matuto.boot.common.domain.LoginUser;
import com.matuto.boot.common.utils.PasswordUtils;
import com.matuto.boot.common.utils.SecurityUtils;
import com.matuto.boot.system.entity.SysUser;
import com.matuto.boot.system.service.LoginService;
import com.matuto.boot.system.service.SysUserService;
import com.matuto.boot.common.exception.ServiceException;
import com.matuto.boot.system.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 登录服务实现类
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final SysUserService userService;
    private final CaptchaService captchaService;

    @Override
    public String login(String username, String password, String code, String uuid) {
        // 验证码校验
        if (!captchaService.validate(uuid, code)) {
            throw new ServiceException("验证码错误");
        }
        // 用户验证
        SysUser user = userService.selectUserByUserName(username);
        if (user == null) {
            throw new ServiceException("登录用户不存在");
        }
        if (UserConstants.USER_DISABLE.equals(user.getStatus())) {
            throw new ServiceException("用户已停用，请联系管理员");
        }
        // 密码验证
        if (!PasswordUtils.verify(password, user.getSalt(), user.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        // 生成token
        StpUtil.login(user.getId());
        // 设置用户信息
        LoginUser loginUser = new LoginUser()
                .setUserId(user.getId())
                .setToken(StpUtil.getTokenValue())
                .setLoginTime(System.currentTimeMillis())
                .setExpireTime(StpUtil.getTokenTimeout() * 1000)
                .setUser(user)
                .setUserType(user.getUserType());
        // 设置权限信息
        Set<String> permissions = new HashSet<>();
        // TODO: 获取用户权限
        loginUser.setPermissions(permissions);
        // 保存用户信息
        StpUtil.getSession().set("loginUser", loginUser);
        return StpUtil.getTokenValue();
    }

    @Override
    public LoginUser getInfo() {
        return SecurityUtils.getLoginUser();
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }
} 