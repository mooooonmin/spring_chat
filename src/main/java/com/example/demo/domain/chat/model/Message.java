package com.example.demo.domain.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String to;
    private String from;
    private String message;

}
