package com.ggz.algorithm;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantTest {
    static Lock lock = new ReentrantLock();
    static int value = 0;
    static ExecutorService executorService = Executors.newFixedThreadPool(100);

    public static void main(String[] args) throws InterruptedException, MQBrokerException, RemotingException, MQClientException {

//        for (int i = 100; i < 1000; i=i+100) {
//            int finalI = i;
//            Thread thread = new Thread(new Runnable() {
//                HashSet<Integer> set = new HashSet<Integer>();
//                @Override
//                public void run() {
//                    set = ss(finalI);
//                    System.out.println(set.toString());
//                    System.out.println(set.size());
//                    System.out.println("=============================");
//
//                }
//            });
//            executorService.execute(thread);
//        }

    }

    public static void testRocketMq() throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        // 生产者配置
        DefaultMQProducer producer = new DefaultMQProducer("OrderProducer");
        producer.setNamesrvAddr("localhost:9876");

        // 发送全局顺序消息
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("GlobalTopic", "TagA", ("Order-" + i).getBytes());
            producer.send(msg,new MessageQueueSelector(){
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    return list.get(0);
                }
            },null);
        }


        // 消费者配置
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("OrderConsumer");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe("GlobalTopic", "*");

        // 处理消息（全局顺序）
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println("收到全局顺序消息: " + new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
    }

    public static HashSet<Integer> ss(int var){
        HashSet<Integer> set = new HashSet<>();
        int i;
        for (i = 0 ; i < var ; i++){
            set.add(i);
        }
        return set;
    }


    public static void get() {
        // 获取锁
        try {
            if (lock.tryLock()) {
                System.out.println((value++) + "," + Thread.currentThread().getName());
            }
            lock.unlock();
        } catch (Exception e) {
            try {
                Thread.sleep(100);
                get();
            } catch (Exception ee) {
                System.out.println("睡眠失败！");
            }
        }
    }
}
