package com.feign.client;

import org.springframework.stereotype.Component;

/**
 * Feign客户端降级处理类
 * 使用Resilience4j替代Hystrix
 */
@Component
public class OpenFeignClientFallback implements OpenFeignClient {
    @Override
    public String getPort() {
        return "服务暂时不可用";
    }
}

