package com.springboot.crd.enums;

import com.springboot.gen.enums.BaseErrorMessage;

public enum CrdErrorMessage implements BaseErrorMessage {

    INVALID_CREDIT_CARD("Invalid credit card!"),
    INSUFFICIENT_CREDIT_CARD_LIMIT("Insufficient credit card limit!"),
    CREDIT_CARD_EXPIRED("Credit card expired!")
    ;

    private String message;
    CrdErrorMessage(String message) {
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
