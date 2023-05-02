package com.springboot.acc.enums;

import com.springboot.gen.enums.BaseErrorMessage;

public enum AccErrorMessage implements BaseErrorMessage {

    INSUFFICIENT_BALANCE("Insufficient balance!"),
    ;

    private String message;

    AccErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
