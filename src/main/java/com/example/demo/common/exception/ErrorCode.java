package com.example.demo.common.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode implements CodeInterface {

    SUCCESS(0, "Success"),
    USER_ALREADY_EXISTS(-1, "USER_ALREADY_EXISTS"),
    USER_SAVED_FAILED(-2, "USER_SAVED_FAILED"),
    NOT_EXIST_USER(-3, "NOT_EXIST_USER"),
    MISS_MATCH_PASSWORD(-4, "MISS_MATCH_PASSWORD");

    private final Integer code;
    private final String message;

}
