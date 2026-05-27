package com.pulsewave.chatservice.repository;

import com.pulsewave.chatservice.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository
        extends JpaRepository<Message, Long> {

    List<Message> findBySenderAndReceiverOrReceiverAndSender(
            String sender,
            String receiver,
            String receiver2,
            String sender2
    );
}