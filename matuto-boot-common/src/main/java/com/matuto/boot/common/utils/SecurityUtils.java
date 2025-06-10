package com.matuto.boot.common.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.matuto.boot.common.domain.LoginUser;
import com.matuto.boot.common.exception.ServiceException;

/**
 * 安全服务工具类
 */
public class SecurityUtils {
    /**
     * 获取用户账户
     **/
    public static String getUsername() {
        try {
            return StpUtil.getLoginIdAsString();
        } catch (Exception e) {
            throw new ServiceException("获取用户账户异常");
        }
    }

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        try {
            return (LoginUser) StpUtil.getSession().get("loginUser");
        } catch (Exception e) {
            throw new ServiceException("获取用户信息异常");
        }
    }

    /**
     * 获取用户ID
     */
    public static Long getLoginUserId() {
        try {
            return getLoginUser().getId();
        } catch (Exception e) {
            throw new ServiceException("获取用户ID异常");
        }
    }

    /**
     * 获取用户类型
     */
    public static String getUserType() {
        try {
            LoginUser loginUser = getLoginUser();
            return loginUser != null ? loginUser.getUserType() : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 是否为管理员
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    /**
     * 生成密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        String salt = PasswordUtils.generateSalt();
        return PasswordUtils.encrypt(password, salt);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword 真实密码
     * @param salt 盐值
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String salt, String encodedPassword) {
        return PasswordUtils.verify(rawPassword, salt, encodedPassword);
    }
} 