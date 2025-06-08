package com.matuto.boot.system.domain.vo;

import lombok.Data;

@Data
public class LoginVO {
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 验证码
     */
    private String captcha;
    
    /**
     * 验证码标识
     */
    private String uuid;
    
    /**
     * 记住我
     */
    private Boolean rememberMe;
} 