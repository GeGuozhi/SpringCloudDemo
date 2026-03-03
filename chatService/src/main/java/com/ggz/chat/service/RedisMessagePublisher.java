package com.ggz.chat.service;

import com.alibaba.fastjson.JSON;
import com.ggz.chat.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


/**
 * Redis消息发布服务
 * 用于分布式消息广播
 * 
 * @author ggz
 */
@Slf4j
@Service
public class RedisMessagePublisher {

    private static final String CHAT_CHANNEL = "chat:channel";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发布消息到Redis频道
     * 
     * @param message 聊天消息
     */
    public void publish(ChatMessage message) {
        try {
            String jsonMessage = JSON.toJSONString(message);
            redisTemplate.convertAndSend(CHAT_CHANNEL, jsonMessage);
            log.debug("发布消息到Redis频道: {}", jsonMessage);
        } catch (Exception e) {
            log.error("发布消息到Redis失败", e);
        }
    }
}

