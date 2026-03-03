package com.ggz;


import cn.hutool.core.net.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;

/**
 * @author guozhi.ge
 * @date 07/13/2020
 */

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        // 获取激活的profile，默认为local
        String activeProfile = System.getProperty("spring.profiles.active", "local");
        
        // 根据profile设置不同的端口检查
        int port = getPortByProfile(activeProfile);
        
        if (!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用了，无法启动%n", port);
            System.exit(1);
        }

        System.out.println("==========================================");
        System.out.println("启动Eureka Server");
        System.out.println("激活的Profile: " + activeProfile);
        System.out.println("服务端口: " + port);
        System.out.println("==========================================");

        new SpringApplicationBuilder(EurekaServerApplication.class)
                .properties("server.port=" + port)
                .properties("spring.profiles.active=" + activeProfile)
                .run(args);
    }
    
    /**
     * 根据profile获取端口
     */
    private static int getPortByProfile(String profile) {
        return Integer.parseInt(System.getProperty("server.port", "8761"));
    }
}
