package com.example.demo.domain.chat.controller;

import com.example.demo.domain.chat.model.response.ChatListResponse;
import com.example.demo.domain.chat.service.ChatserviceV1;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Chat API", description = "V1 Chat API")
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatControllerV1 {

    private final ChatserviceV1 chatserviceV1;

    @Operation(
            summary = "채팅 리스트 가져옴",
            description = "가장 최근 10개의 채팅 리스트 반환"
    )
    @GetMapping("/chat-list")
    public ChatListResponse chatList(
            @RequestParam("name") @Valid String to,
            @RequestParam("from") @Valid String from
    ) {
        return chatserviceV1.chatList(from, to);
    }

}
