package com.psbc.invres.resarchlibrary.exception;

import com.psbc.invres.resarchlibrary.entity.IResponseCode;

/**
 * 业务异常，抛出后由全局异常处理器统一转换为 Response 返回给前端。
 */
public class BizException extends RuntimeException {

    private String code;
    private String message;

    public BizException(IResponseCode responseCode) {
        super(responseCode.getMsg());
        this.code = responseCode.getCode();
        this.message = responseCode.getMsg();
    }

    public BizException(IResponseCode responseCode, String extraMsg) {
        super(responseCode.getMsg() + "：" + extraMsg);
        this.code = responseCode.getCode();
        this.message = responseCode.getMsg() + "：" + extraMsg;
    }

    /**
     * 兼容旧代码的 int 码构造函数
     */
    public BizException(int code, String message) {
        super(message);
        this.code = String.valueOf(code);
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
