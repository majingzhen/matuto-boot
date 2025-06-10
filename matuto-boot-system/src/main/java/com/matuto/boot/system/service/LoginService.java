package com.matuto.boot.system.service;

import com.matuto.boot.common.domain.LoginUser;
import com.matuto.boot.system.domain.vo.LoginUserVO;
import com.matuto.boot.system.domain.vo.LoginVO;

/**
 * 登录服务接口
 */
public interface LoginService {
    /**
     * 登录
     *
     * @param loginVO 登录信息
     * @return 结果
     */
    String login(LoginVO loginVO);

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