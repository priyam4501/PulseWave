package com.pulsewave.chatservice.service;

import com.pulsewave.chatservice.dto.ChatMessage;
import com.pulsewave.chatservice.entity.Message;
import com.pulsewave.chatservice.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final MessageRepository messageRepository;

    @Override
    public void saveMessage(ChatMessage chatMessage) {

        Message message = Message.builder()

                .sender(chatMessage.getSender())

                .receiver(chatMessage.getReceiver())

                .content(chatMessage.getContent())

                .timestamp(LocalDateTime.now())

                .build();

        messageRepository.save(message);
    }
}