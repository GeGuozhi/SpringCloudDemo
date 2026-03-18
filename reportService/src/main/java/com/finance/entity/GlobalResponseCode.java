package com.finance.entity;

/**
 * @author ggz on 2026/3/16
 */
/**
 * 全局响应码枚举
 */
public enum GlobalResponseCode {

    // 成功响应码
    SUCCESS(200, "操作成功"),

    // 客户端错误码 400-499
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源未找到"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    REQUEST_TIMEOUT(408, "请求超时"),

    // 服务器错误码 500-599
    ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务暂时不可用"),
    GATEWAY_TIMEOUT(504, "网关超时"),

    // 业务错误码 1000-1999
    BUSINESS_ERROR(1000, "业务处理失败"),
    VALIDATION_ERROR(1001, "参数验证失败"),
    DATA_NOT_EXIST(1002, "数据不存在"),
    DATA_ALREADY_EXIST(1003, "数据已存在"),
    OPERATION_FAILED(1004, "操作失败"),

    // 认证授权错误码 2000-2999
    TOKEN_INVALID(2001, "Token无效或已过期"),
    TOKEN_EXPIRED(2002, "Token已过期"),
    ACCESS_DENIED(2003, "访问权限不足"),
    LOGIN_FAILED(2004, "登录失败"),

    // 系统错误码 9000-9999
    SYSTEM_ERROR(9000, "系统异常"),
    DATABASE_ERROR(9001, "数据库操作异常"),
    NETWORK_ERROR(9002, "网络异常"),
    FILE_OPERATION_ERROR(9003, "文件操作异常");

    private final int code;
    private final String message;

    GlobalResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 根据code查找对应的枚举
     */
    public static GlobalResponseCode getByCode(int code) {
        for (GlobalResponseCode responseCode : values()) {
            if (responseCode.getCode() == code) {
                return responseCode;
            }
        }
        return ERROR;
    }

    /**
     * 判断是否是成功响应码
     */
    public boolean isSuccess() {
        return this == SUCCESS || code >= 200 && code < 300;
    }
}