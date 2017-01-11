package ru.vat78.homeMoney.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.vat78.homeMoney.model.exceptions.DbOperationException;
import ru.vat78.homeMoney.model.exceptions.ValidationException;
import ru.vat78.homeMoney.model.exceptions.WrongTypeException;

import java.util.Map;

@Controller
public class ExceptionHandlingController {

    @ExceptionHandler(WrongTypeException.class)
    public ResponseEntity<String> handleNotFounds(Exception e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String,String>> handleValidationErrors(Exception e) {
        ((ValidationException) e).getErrors();
        return new ResponseEntity<Map<String, String>>(((ValidationException) e).getErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DbOperationException.class)
    public ResponseEntity<String> handleDbErrors(Exception e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
    }
}