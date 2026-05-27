package com.pulsewave.chatservice.service;

import com.pulsewave.chatservice.dto.ChatMessage;
import com.pulsewave.chatservice.entity.Message;

import java.util.List;

public interface ChatService {

    void saveMessage(ChatMessage message);

    List<Message> getConversation(
            String sender,
            String receiver
    );
}