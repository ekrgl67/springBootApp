package com.springboot.gen.enums;

public enum GenErrorMessage {

    ITEM_NOT_FOUND("Item Not Found!");

    private String massage;

    GenErrorMessage(String massage) {
        this.massage = massage;
    }

    public String getMassage(){
        return massage;
    }
    @Override
    public String toString() {
        return massage;
    }
}
