package com.ggz;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 启动器
 *
 * @author ggz on 2022/9/19
 */
@SpringBootApplication
public class dubboProviderApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(dubboProviderApplication.class).run(args);
    }
}