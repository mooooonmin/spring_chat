package com.example.demo.domain.chat.service;

import com.example.demo.domain.chat.model.Message;
import com.example.demo.domain.repository.ChatRepository;
import com.example.demo.domain.repository.entity.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@RequiredArgsConstructor
@Service
public class ChatserviceV1 {

    private final ChatRepository chatRepository;

    @Transactional(transactionManager = "createChatTransactionManager")
    public void saveChatMessage(Message msg) {

        Chat chat = Chat.builder()
                .sender(msg.getFrom())
                .receiver(msg.getTo())
                .message(msg.getMessage())
                .created_at(new Timestamp(System.currentTimeMillis()))
                .build();

        chatRepository.save(chat);
    }

}
