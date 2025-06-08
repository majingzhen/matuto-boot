package com.matuto.boot.common.exception;

/**
 * 自定义异常
 *
 * @author Matuto
 */
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public CustomException() {
    }

    public CustomException(String message) {
        this.message = message;
    }

    public CustomException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public CustomException setMessage(String message) {
        this.message = message;
        return this;
    }

    public CustomException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }
}