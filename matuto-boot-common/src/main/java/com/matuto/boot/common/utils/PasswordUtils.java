package com.matuto.boot.common.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * 密码工具类
 */
public class PasswordUtils {
    /**
     * 生成随机盐
     */
    public static String generateSalt() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(16);
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 密码加密
     *
     * @param password 密码
     * @param salt 盐值
     * @return 加密后的密码
     */
    public static String encrypt(String password, String salt) {
        return DigestUtils.md5DigestAsHex((password + salt).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 密码验证
     *
     * @param password 密码
     * @param salt 盐值
     * @param encryptedPassword 加密后的密码
     * @return 验证结果
     */
    public static boolean verify(String password, String salt, String encryptedPassword) {
        return encrypt(password, salt).equals(encryptedPassword);
    }
} 