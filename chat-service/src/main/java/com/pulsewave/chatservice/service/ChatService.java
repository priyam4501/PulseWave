package com.pulsewave.chatservice.service;

import com.pulsewave.chatservice.dto.ChatMessage;

public interface ChatService {
    void saveMessage(ChatMessage message);
}