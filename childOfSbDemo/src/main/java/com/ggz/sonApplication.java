package com.ggz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
//@ServletComponentScan("com.ggz.servlet")
public class sonApplication {
    public static void main(String[] args) {
        new SpringApplication(sonApplication.class).run(args);
    }
}
