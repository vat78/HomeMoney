package ru.vat78.homeMoney.model.exceptions;

public class WrongTypeException extends Exception {

    public WrongTypeException(String type) {
        super(String.format("edit.error.noType", type));
    }

}
