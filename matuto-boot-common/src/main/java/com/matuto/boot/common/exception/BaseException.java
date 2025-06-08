package com.matuto.boot.common.exception;

import lombok.Getter;

import java.io.Serial;

/**
 * 基础异常
 *
 * @author Matuto
 */
@Getter
public class BaseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

    public BaseException(String module, Integer code, Object[] args, String defaultMessage) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public BaseException(String module, String defaultMessage) {
        this(module, null, null, defaultMessage);
    }

    public BaseException(Integer code, Object[] args) {
        this(null, code, args, null);
    }

    public BaseException(String defaultMessage) {
        this(null, null, null, defaultMessage);
    }

    @Override
    public String getMessage() {
        String message = null;
        if (defaultMessage != null) {
            message = defaultMessage;
        }
        // TODO: Consider i18n for messages
        // if (code != null) {
        //     message = MessageUtils.message(code, args);
        // }
        if (message == null) {
            message = super.getMessage();
        }
        return message;
    }
}