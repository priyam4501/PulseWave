package com.pulsewave.chatservice.service;

import com.pulsewave.chatservice.dto.ChatMessage;
import com.pulsewave.chatservice.entity.Message;
import com.pulsewave.chatservice.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public List<Message> getConversation(String sender, String receiver) {

        return messageRepository
                .findBySenderAndReceiverOrReceiverAndSender(
                        sender,
                        receiver,
                        sender,
                        receiver
                );
    }
}