package com.ggz.chat.service;

import com.alibaba.fastjson.*;
import com.ggz.chat.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Redis消息订阅服务
 * 接收Redis频道消息并广播给WebSocket客户端
 * 
 * @author ggz
 */
@Slf4j
@Service
public class RedisMessageSubscriber {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 处理从Redis接收到的消息
     * 这个方法会被RedisMessageListener调用
     * 
     * @param message JSON格式的消息
     */
    public void handleMessage(String message) {
        try {
            ChatMessage chatMessage = JSON.parseObject(message, ChatMessage.class);
            log.debug("从Redis接收到消息: {}", chatMessage);
            // 广播消息给所有WebSocket客户端
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        } catch (Exception e) {
            log.error("处理Redis消息失败", e);
        }
    }
}

