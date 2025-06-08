package com.matuto.boot.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.matuto.boot.common.domain.AjaxResult;
import com.matuto.boot.common.constant.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import com.matuto.boot.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.nio.file.AccessDeniedException;
import java.util.Set;

/**
 * 全局异常处理器
 *
 * @author Matuto
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {



    /**
     * 基础异常
     */
    @ExceptionHandler(BaseException.class)
    public AjaxResult baseException(BaseException e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error(e.getDefaultMessage());
    }

        /**
     * 处理业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public AjaxResult handleServiceException(ServiceException e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error(HttpStatus.ERROR, e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(CustomException.class)
    public AjaxResult handleCustomException(CustomException e) {
        log.error("自定义异常：{}", e.getMessage(), e);
        return AjaxResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    public AjaxResult handleNotLoginException(NotLoginException nle, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.info("请求地址'{}',认证失败'{}',无法访问系统资源", requestURI, nle.getMessage());
        return AjaxResult.error(HttpStatus.UNAUTHORIZED, "认证失败，无法访问系统资源");
    }

    /**
     * 角色权限异常
     */
    @ExceptionHandler(NotRoleException.class)
    public AjaxResult handleNotRoleException(NotRoleException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',角色权限校验不通过'{}'", requestURI, e.getMessage());
        return AjaxResult.error(HttpStatus.FORBIDDEN, "没有访问权限，请联系管理员授权");
    }

    /**
     * 权限码异常
     */
    @ExceptionHandler(NotPermissionException.class)
    public AjaxResult handleNotPermissionException(NotPermissionException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限码校验不通过'{}'", requestURI, e.getMessage());
        return AjaxResult.error(HttpStatus.FORBIDDEN, "没有访问权限，请联系管理员授权");
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.error("请求方法不支持：{}", e.getMessage(), e);
        return AjaxResult.error(HttpStatus.BAD_METHOD, "请求方法不支持");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常：{}", e.getMessage(), e);
        String requestURI = request.getRequestURI();
        String message = String.format("请求地址'%s'发生异常，请联系管理员", requestURI);
        return AjaxResult.error(HttpStatus.ERROR, message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException e) {
        log.error("参数绑定异常：{}", e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return AjaxResult.error(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数校验异常：{}", e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return AjaxResult.error(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * 处理权限异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handleAccessDeniedException(AccessDeniedException e) {
        log.error("权限异常：{}", e.getMessage(), e);
        return AjaxResult.error(HttpStatus.FORBIDDEN, "没有权限，请联系管理员");
    }

    /**
     * 处理参数类型不匹配异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public AjaxResult handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("参数类型不匹配：{}", e.getMessage(), e);
        return AjaxResult.error(HttpStatus.BAD_REQUEST, "参数类型不匹配");
    }

    /**
     * 处理缺少请求参数异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public AjaxResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("缺少请求参数：{}", e.getMessage(), e);
        return AjaxResult.error(HttpStatus.BAD_REQUEST, "缺少请求参数");
    }

    /**
     * 处理约束违反异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public AjaxResult handleConstraintViolationException(ConstraintViolationException e) {
        log.error("约束违反异常：{}", e.getMessage(), e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("参数校验失败");
        return AjaxResult.error(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * 处理文件上传大小限制异常
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public AjaxResult handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("文件上传大小限制：{}", e.getMessage(), e);
        return AjaxResult.error(HttpStatus.BAD_REQUEST, "文件大小超出限制");
    }
}