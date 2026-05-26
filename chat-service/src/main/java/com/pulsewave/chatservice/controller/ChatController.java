package com.pulsewave.chatservice.controller;

import com.pulsewave.chatservice.dto.ChatMessage;
import com.pulsewave.chatservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat")

    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {

        log.info(
                "Message received from {} to {} : {}",
                message.getSender(),
                message.getReceiver(),
                message.getContent()
        );

        chatService.saveMessage(message);

        return message;
    }
}