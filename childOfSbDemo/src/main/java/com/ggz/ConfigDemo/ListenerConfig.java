package com.ggz.ConfigDemo;


import com.ggz.listener.StartListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerConfig {

    @Bean
    public StartListener startUpListener(){
        return new StartListener();
    }
}
