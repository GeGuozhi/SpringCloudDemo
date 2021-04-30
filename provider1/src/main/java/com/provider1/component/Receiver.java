package com.provider1.component;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "helloRabbit")
public class Receiver {

    @RabbitHandler
    public void process(String hello){
        System.out.println("Receiver:"+hello);
    }


}
