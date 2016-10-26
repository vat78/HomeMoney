package ru.vat78.homeMoney.controller.api;

import java.io.Serializable;

class ErrorMessage implements Serializable {

    private String fieldName;
    private LocaleMessage message;

    ErrorMessage(String fieldName, LocaleMessage message) {
        this.fieldName = fieldName;
        this.message = message;
    }
    String getFieldName() {
        return fieldName;
    }
    LocaleMessage getMessage() {
        return message;
    }
}
