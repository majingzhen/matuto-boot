package com.matuto.boot.system.service.impl;

import com.google.code.kaptcha.Producer;
import com.matuto.boot.system.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务实现类
 */
@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {

    private final Producer captchaProducer;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String CAPTCHA_CODE_KEY = "captcha_codes:";
    private static final Integer CAPTCHA_EXPIRATION = 2;

    @Override
    public BufferedImage createCaptcha(String uuid) {
        // 生成验证码
        String capText = captchaProducer.createText();
        // 保存验证码信息
        redisTemplate.opsForValue().set(CAPTCHA_CODE_KEY + uuid, capText, CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 生成验证码图片
        return captchaProducer.createImage(capText);
    }

    @Override
    public boolean validate(String uuid, String code) {
        if (uuid == null || code == null) {
            return false;
        }
        String key = CAPTCHA_CODE_KEY + uuid;
        String captcha = redisTemplate.opsForValue().get(key);
        // 删除验证码
        redisTemplate.delete(key);
        return code.equalsIgnoreCase(captcha);
    }
} 