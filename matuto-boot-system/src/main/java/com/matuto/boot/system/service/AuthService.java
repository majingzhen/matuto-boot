package com.matuto.boot.system.service;

import com.matuto.boot.system.domain.vo.LoginUserPerVO;
import com.matuto.boot.system.domain.vo.LoginUserVO;
import com.matuto.boot.system.domain.vo.LoginVO;
import com.matuto.boot.system.entity.SysUser;

import java.util.List;

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
    LoginUserVO getUserInfo();


    LoginUserPerVO getUserPerms();
}