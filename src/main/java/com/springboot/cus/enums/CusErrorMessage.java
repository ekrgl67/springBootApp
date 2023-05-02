package com.springboot.cus.enums;

import com.springboot.gen.enums.BaseErrorMessage;

public enum CusErrorMessage implements BaseErrorMessage {

    CUSTOMER_ERROR_MESSAGE("Customer not found!"),
            ;

    private String message;

    CusErrorMessage(String message) {
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
