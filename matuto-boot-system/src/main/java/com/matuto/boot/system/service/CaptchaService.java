package com.matuto.boot.system.service;

import java.awt.image.BufferedImage;

/**
 * 验证码服务接口
 */
public interface CaptchaService {
    /**
     * 生成验证码
     *
     * @param uuid 唯一标识
     * @return 验证码图片
     */
    BufferedImage createCaptcha(String uuid);

    /**
     * 验证验证码
     *
     * @param uuid 唯一标识
     * @param code 验证码
     * @return 结果
     */
    boolean validate(String uuid, String code);
} 