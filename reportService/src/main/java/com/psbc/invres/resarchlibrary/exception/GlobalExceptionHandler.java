package com.psbc.invres.resarchlibrary.exception;

import com.psbc.invres.resarchlibrary.entity.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理：
 * - 拦截 BizException，返回统一 Response
 * - 其它异常简单打日志并返回泛化的服务器错误
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BizException.class)
    public Response<Void> handleBizException(BizException ex, HttpServletResponse response) {
        log.warn("BizException: code={}, message={}", ex.getCode(), ex.getMessage());
        forceJsonResponseIfNeeded(response);
        try {
            int code = Integer.parseInt(ex.getCode());
            return Response.error(code, ex.getMessage());
        } catch (NumberFormatException e) {
            return Response.error(400, ex.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public Response<Void> handleException(Exception ex, HttpServletResponse response) {
        log.error("Unhandled exception", ex);
        forceJsonResponseIfNeeded(response);
        return Response.error(500, "服务器内部错误");
    }

    /**
     * 导出接口会设置 Content-Type 为 zip/xlsx；
     * 若后续抛异常，全局异常处理要返回 JSON，需要把响应切回 application/json。
     */
    private static void forceJsonResponseIfNeeded(HttpServletResponse response) {
        if (response == null) {
            return;
        }
        if (response.isCommitted()) {
            return;
        }

        String ct = response.getContentType();
        if (ct == null) {
            return;
        }

        String c = ct.toLowerCase();
        boolean isBinary = c.contains("application/zip")
                || c.contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                || c.contains("application/octet-stream");

        if (isBinary) {
            try {
                response.resetBuffer();
            } catch (Exception ignored) {
                // ignore
            }
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
        }
    }
}
