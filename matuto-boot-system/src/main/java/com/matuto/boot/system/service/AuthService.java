package com.matuto.boot.system.service;

import com.matuto.boot.system.domain.vo.LoginVO;

public interface AuthService {
    /**
     * 登录
     *
     * @param loginVO 登录信息
     * @return token
     */
    String login(LoginVO loginVO);
    
    /**
     * 登出
     */
    void logout();
    
    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    Object getUserInfo();
} 