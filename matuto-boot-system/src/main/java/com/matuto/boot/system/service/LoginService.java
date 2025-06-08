package com.matuto.boot.system.service;

import com.matuto.boot.common.domain.LoginUser;

/**
 * 登录服务接口
 */
public interface LoginService {
    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    String login(String username, String password, String code, String uuid);

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    LoginUser getInfo();

    /**
     * 退出登录
     */
    void logout();
} 