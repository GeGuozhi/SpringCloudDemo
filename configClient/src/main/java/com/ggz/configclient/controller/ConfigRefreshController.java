package com.ggz.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RefreshScope
public class ConfigRefreshController {

    @Value("${version:unknown}")
    private String version;

    @Value("${bobo.user.name:unknown}")
    private String userName;

    @Autowired
    private Environment environment;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    // 获取当前配置状态
    @GetMapping("/config-status")
    public Map<String, Object> getConfigStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("当前版本", version);
        status.put("用户名", userName);
        status.put("配置服务器", environment.getProperty("spring.cloud.config.uri"));
        status.put("激活Profile", environment.getProperty("spring.profiles.active"));
        status.put("Git分支", environment.getProperty("spring.cloud.config.label"));
        status.put("最后检查时间", new Date());
        return status;
    }

    // 手动触发刷新
    @PostMapping("/manual-refresh")
    public Map<String, Object> manualRefresh() {
        Map<String, Object> result = new HashMap<>();
        result.put("刷新前版本", version);
        result.put("刷新前用户名", userName);

        // 手动发布刷新事件
        eventPublisher.publishEvent(new RefreshScopeRefreshedEvent());

        result.put("刷新后版本", version);
        result.put("刷新后用户名", userName);
        result.put("刷新时间", new Date());
        result.put("状态", "手动刷新完成");

        return result;
    }

    // 监听刷新事件
    @EventListener
    public void handleRefresh(RefreshScopeRefreshedEvent event) {
        System.out.println("=== 配置刷新事件触发 ===");
        System.out.println("刷新后版本: " + version);
        System.out.println("刷新后用户名: " + userName);
        System.out.println("刷新时间: " + new Date());
    }
}