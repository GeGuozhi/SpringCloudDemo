package com.provider1.component;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProviderSender {
    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(){
        String content = "hello "+new Date();
        System.out.println(content);
        amqpTemplate.convertAndSend("exchange","topic.message","message");
        amqpTemplate.convertAndSend("exchange","topic.messages","messagesss");
    }
}
