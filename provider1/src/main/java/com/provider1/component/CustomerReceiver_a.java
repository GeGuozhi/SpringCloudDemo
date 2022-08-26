package com.provider1.component;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * simple模式
 */
@Component
@RabbitListener(queues = "topic.simple.a")
public class CustomerReceiver_a {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("topic.simple.a:" + hello);
    }
}

@Component
@RabbitListener(queues = "topic.simple.a")
class Customer_simple_a_1 {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("topic.simple.a_1:" + hello);
    }
}

@Component
@RabbitListener(queues = "topic.simple.b")
class Customer_simple_b {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("topic.simple.b:" + hello);
    }
}

@Component
@RabbitListener(queues = "topic.simple.b")
class CustomerReceiver_b_1 {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("topic.simple.b_1:" + hello);
    }
}

@Component
@RabbitListener(queues = "topic.simple.c")
class Customer_simple_c {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("topic.simple.c:" + hello);
    }
}

@Component
@RabbitListener(queues = "topic.simple.c")
class CustomerReceiver_c_1 {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("topic.simple.c_1:" + hello);
    }
}

/**
 * fanoutExchange模式
 */
@Component
@RabbitListener(queues = "topic.fanoutExchange.a")
class Customer_fanoutExchange_a {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("topic.fanoutExchange.a:" + hello);
    }
}

@Component
@RabbitListener(queues = "topic.fanoutExchange.b")
class Customer_fanoutExchange_b {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("topic.fanoutExchange.b:" + hello);
    }
}

@Component
@RabbitListener(queues = "topic.fanoutExchange.c")
class Customer_fanoutExchange_c {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("topic.fanoutExchange.c:" + hello);
    }
}

/**
 * directExchange模式
 */
@Component
@RabbitListener(queues = "topic.directExchange.a")
class Customer_directExchange_a {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("topic.directExchange.a:" + hello);
    }
}

@Component
@RabbitListener(queues = "topic.directExchange.b")
class Customer_directExchange_b {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("topic.directExchange.b:" + hello);
    }
}

@Component
@RabbitListener(queues = "topic.directExchange.c")
class Customer_directExchange_c {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("topic.directExchange.c:" + hello);
    }
}

/**
 * topicExchange模式
 */
@Component
@RabbitListener(queues = "topic.topicExchange.a")
class Customer_topicExchange_a {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("topic.topicExchange.a:" + hello);
    }
}

@Component
@RabbitListener(queues = "topic.topicExchange.b")
class Customer_topicExchange_b {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("topic.topicExchange.b:" + hello);
    }
}

@Component
@RabbitListener(queues = "topic.topicExchange.c")
class Customer_topicExchange_c {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("topic.topicExchange.c:" + hello);
    }
}