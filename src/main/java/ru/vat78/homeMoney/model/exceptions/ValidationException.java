package ru.vat78.homeMoney.model.exceptions;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ValidationException extends Exception {

    private Map<String, String> errors = new HashMap<String, String>();

    public ValidationException(Set<ConstraintViolation> errors) {

        super();
        for (ConstraintViolation error : errors) {
            this.errors.put(error.getPropertyPath().toString(), error.getMessage());
        }

    }

    public ValidationException(String field, String message) {

        super();
        errors.put(field,message);

    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
