package com.finance.entity;

import java.io.Serializable;

/**
 * @author ggz on 2026/3/16
 */
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一响应结果类
 *
 * @param <T> 数据泛型
 */
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    // 响应码
    private int code;

    // 响应消息
    private String message;

    // 响应数据
    private T data;

    // 时间戳
    private long timestamp;

    // 扩展字段
    private Map<String, Object> extras;

    public Response() {
        this.timestamp = System.currentTimeMillis();
    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public Response(GlobalResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.timestamp = System.currentTimeMillis();
    }

    public Response(GlobalResponseCode responseCode, T data) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    // ================== 静态工厂方法 ==================

    /**
     * 成功响应（无数据）
     */
    public static <T> Response<T> success() {
        return new Response<>(GlobalResponseCode.SUCCESS);
    }

    /**
     * 成功响应（有数据）
     */
    public static <T> Response<T> success(T data) {
        return new Response<>(GlobalResponseCode.SUCCESS, data);
    }

    /**
     * 成功响应（有数据和自定义消息）
     */
    public static <T> Response<T> success(String message, T data) {
        Response<T> response = new Response<>(GlobalResponseCode.SUCCESS.getCode(), message, data);
        return response;
    }

    /**
     * 失败响应（使用默认错误码）
     */
    public static <T> Response<T> error() {
        return new Response<>(GlobalResponseCode.ERROR);
    }

    /**
     * 失败响应（自定义错误消息）
     */
    public static <T> Response<T> error(String message) {
        return new Response<T>(GlobalResponseCode.ERROR.getCode(), message, null);
    }

    /**
     * 失败响应（使用指定的响应码）
     */
    public static <T> Response<T> error(GlobalResponseCode responseCode) {
        return new Response<>(responseCode);
    }

    /**
     * 失败响应（自定义响应码和消息）
     */
    public static <T> Response<T> error(int code, String message) {
        return new Response<>(code, message, null);
    }

    /**
     * 失败响应（使用指定的响应码和自定义消息）
     */
    public static <T> Response<T> error(GlobalResponseCode responseCode, String message) {
        return new Response<>(responseCode.getCode(), message, null);
    }

    /**
     * 失败响应（有数据）
     */
    public static <T> Response<T> error(GlobalResponseCode responseCode, T data) {
        return new Response<>(responseCode, data);
    }

    /**
     * 失败响应（自定义响应码、消息和数据）
     */
    public static <T> Response<T> error(int code, String message, T data) {
        return new Response<>(code, message, data);
    }

    // ================== Builder 模式 ==================

    /**
     * 创建Response构建器
     */
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    /**
     * Response构建器
     */
    public static class Builder<T> {
        private int code = GlobalResponseCode.SUCCESS.getCode();
        private String message = GlobalResponseCode.SUCCESS.getMessage();
        private T data;
        private Map<String, Object> extras;

        public Builder<T> code(int code) {
            this.code = code;
            return this;
        }

        public Builder<T> code(GlobalResponseCode responseCode) {
            this.code = responseCode.getCode();
            this.message = responseCode.getMessage();
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> extra(String key, Object value) {
            if (this.extras == null) {
                this.extras = new HashMap<>();
            }
            this.extras.put(key, value);
            return this;
        }

        public Builder<T> extras(Map<String, Object> extras) {
            this.extras = extras;
            return this;
        }

        public Response<T> build() {
            Response<T> response = new Response<>(this.code, this.message, this.data);
            if (this.extras != null && !this.extras.isEmpty()) {
                response.setExtras(this.extras);
            }
            return response;
        }
    }

    // ================== 工具方法 ==================

    /**
     * 判断响应是否成功
     */
    public boolean isSuccess() {
        return code == GlobalResponseCode.SUCCESS.getCode();
    }

    /**
     * 判断响应是否失败
     */
    public boolean isError() {
        return !isSuccess();
    }

    /**
     * 添加扩展字段
     */
    public Response<T> addExtra(String key, Object value) {
        if (this.extras == null) {
            this.extras = new HashMap<>();
        }
        this.extras.put(key, value);
        return this;
    }

    /**
     * 获取扩展字段
     */
    public Object getExtra(String key) {
        return extras != null ? extras.get(key) : null;
    }

    /**
     * 链式设置数据
     */
    public Response<T> withData(T data) {
        this.data = data;
        return this;
    }

    /**
     * 链式设置消息
     */
    public Response<T> withMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * 链式设置响应码
     */
    public Response<T> withCode(int code) {
        this.code = code;
        return this;
    }

    /**
     * 链式设置响应码
     */
    public Response<T> withCode(GlobalResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        return this;
    }

    // ================== Getter 和 Setter ==================

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, Object> extras) {
        this.extras = extras;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                ", extras=" + extras +
                '}';
    }
}