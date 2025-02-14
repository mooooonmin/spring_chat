package com.example.demo.domain.chat.controller;

import com.example.demo.domain.chat.model.Message;
import com.example.demo.domain.chat.service.ChatserviceV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
@Slf4j
public class WssControllerV1 {

    private final ChatserviceV1 chatserviceV1

    @MessageMapping("/chat/message/{from}")
    @SendTo("/sub/chat")
    public Message receiveMessage(@DestinationVariable String from, Message msg) {
        log.info("Message Receved -> From: {}, to: {}, msg: {}", from, msg.getTo(), msg.getFrom());

        chatserviceV1.saveChatMessage(msg);
        return msg;
    }

}
