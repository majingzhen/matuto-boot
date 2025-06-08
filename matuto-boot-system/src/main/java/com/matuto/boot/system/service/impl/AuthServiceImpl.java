package com.matuto.boot.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.aliyun.oss.ServiceException;
import com.matuto.boot.common.utils.SecurityUtils;
import com.matuto.boot.system.domain.vo.LoginVO;
import com.matuto.boot.system.entity.SysUser;
import com.matuto.boot.system.service.AuthService;
import com.matuto.boot.system.service.CaptchaService;
import com.matuto.boot.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserService userService;
    
    private final CaptchaService captchaService;
    
    @Override
    public String login(LoginVO loginVO) {
        // 验证码校验
        captchaService.validate(loginVO.getUuid(), loginVO.getCaptcha());
        
        // 用户验证
        SysUser user = userService.selectUserByUserName(loginVO.getUsername());
        if (user == null) {
            throw new ServiceException("用户名或密码错误");
        }
        
        // 密码校验
        if (!SecurityUtils.matchesPassword(loginVO.getPassword(),user.getSalt(), user.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        
        // 生成token
        StpUtil.login(user.getId(), loginVO.getRememberMe() ? 7 * 24 * 60 * 60 : 2 * 60 * 60);
        return StpUtil.getTokenValue();
    }
    
    @Override
    public void logout() {
        StpUtil.logout();
    }
    
    @Override
    public SysUser getUserInfo() {
        return userService.getById(StpUtil.getLoginIdAsLong());
    }
} 