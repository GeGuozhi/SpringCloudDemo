package com.finance.exception;

/**
 * 业务异常，抛出后由全局异常处理器统一转换为 Response 返回给前端。
 */
public class BizException extends RuntimeException {

    private int code;
    private String message;

    public BizException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

