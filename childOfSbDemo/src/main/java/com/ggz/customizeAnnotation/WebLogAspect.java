package com.ggz.customizeAnnotation;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
public class WebLogAspect {
    private static final String SPLIT_STRING_M   = "=";
    private static final String SPLIT_STRING_DOT = ", ";

    @Pointcut("@annotation(com.ggz.customizeAnnotation.ActionLog)")
    public void webLog(){
    }

    @Around("webLog() && @annotation(actionLog)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint, ActionLog actionLog) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            // 开始打印请求日志
            HttpServletRequest request = getRequest();

            String urlParams = getRequestParams(request);

            // 打印请求 url
            log.info("请求 URI: {}\t{}", request.getMethod(), request.getRequestURI());
            if (!StringUtils.isEmpty(urlParams)) {
                log.info("请求参数: {}", urlParams);
            }
            log.info("Action:"+actionLog.action());
            log.info("Module:"+actionLog.module());
            String body = (String) request.getAttribute("REQUEST_BODY_ATTR_KEY");
            if (!StringUtils.isEmpty(body)) {
                log.info("请求主体: {}", body);
            }

            stopwatch = Stopwatch.createStarted();
            Object result = proceedingJoinPoint.proceed();
            this.printTimes(stopwatch, result);
            return result;
        }catch (Throwable e) {
            log.error("【{}-{}】 发生异常: ", actionLog.module(), actionLog.action(), e);
            String error = actionLog.error();
            if (!StringUtils.isEmpty(error)) {
                error = actionLog.action() + "失败";
            }
            return e;
        }
    }

    /**
     * 获取请求地址上的参数
     *
     * @param request
     * @return
     */
    private String getRequestParams(HttpServletRequest request) {
        StringBuilder       sb  = new StringBuilder();
        Enumeration<String> enu = request.getParameterNames();
        //获取请求参数
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            sb.append(name).append(SPLIT_STRING_M)
                    .append(request.getParameter(name));
            if (enu.hasMoreElements()) {
                sb.append(SPLIT_STRING_DOT);
            }
        }
        return sb.toString();
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

    private void printTimes(Stopwatch stopwatch, Object result) {
        log.info("响应结果: {}", "");
        log.info("执行耗时: {}ms\n", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}
