package ru.vat78.homeMoney.controller.api;

import java.io.Serializable;

class ErrorMessage implements Serializable {

    private String fieldName;
    private String message;

    ErrorMessage(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
    String getFieldName() {
        return fieldName;
    }
    String getMessage() {
        return message;
    }
}
