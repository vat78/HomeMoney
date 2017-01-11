package ru.vat78.homeMoney.model.exceptions;

import ru.vat78.homeMoney.model.CommonEntry;

public class DbOperationException extends Exception {

    public DbOperationException(String operation, String objectType, Long id) {
        super(String.format("edit.error.dbOperationError", operation, objectType, id));
    }
}
