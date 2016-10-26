package ru.vat78.homeMoney.controller.api;

import java.io.Serializable;

public class LocaleMessage implements Serializable {

    private String message;

    LocaleMessage(String message) { this.message = message;}

    String getMessage() { return message; }

    @Override
    public String toString(){
        return message;
    }
}
