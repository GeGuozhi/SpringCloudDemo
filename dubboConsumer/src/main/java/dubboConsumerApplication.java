package com.ggz;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 启动器
 *
 * @author ggz on 2022/9/19
 */

@SpringBootApplication
public class dubboConsumerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(dubboConsumerApplication.class).run(args);
    }
}