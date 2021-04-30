package com.provider1.component;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Sender {
    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(){
        String content = "hello "+new Date();
        System.out.println(content);
        amqpTemplate.convertAndSend("helloRabbit",content);
    }
}
