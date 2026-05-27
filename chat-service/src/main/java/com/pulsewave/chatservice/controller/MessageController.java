package com.pulsewave.chatservice.controller;

import com.pulsewave.chatservice.entity.Message;
import com.pulsewave.chatservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final ChatService chatService;

    @GetMapping("/{sender}/{receiver}")
    public List<Message> getConversation(@PathVariable String sender, @PathVariable String receiver) {
        return chatService.getConversation(
                sender,
                receiver
        );
    }
}