package com.matuto.boot.system.controller;

import com.matuto.boot.common.domain.AjaxResult;
import com.matuto.boot.system.service.CaptchaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

/**
 * 验证码控制器
 */
@Tag(name = "验证码管理")
@RestController
@RequestMapping("/captcha")
@RequiredArgsConstructor
public class CaptchaController {

    private final CaptchaService captchaService;

    @GetMapping("/get")
    public AjaxResult getCaptcha() {
        String uuid = UUID.randomUUID().toString();
        return AjaxResult.success(captchaService.createCaptcha(uuid));
    }

    @Operation(summary = "获取验证码")
    @GetMapping("/image")
    public void getCaptcha(@RequestParam(value = "uuid", required = false) String uuid, HttpServletResponse response) throws IOException {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
        // 生成验证码
        BufferedImage image = captchaService.createCaptcha(uuid);
        // 输出验证码图片
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.flush();
    }
} 