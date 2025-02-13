package com.example.demo.common.exception;

import lombok.Getter;

@Getter
public class Customexception extends RuntimeException {

    private final CodeInterface codeInterface;

    public Customexception(CodeInterface v) {
        super(v.getMessage());
        this.codeInterface = v;
    }

    public Customexception(CodeInterface v, String message) {
        super(v.getMessage() + message);
        this.codeInterface = v;
    }

}
