package com.amaris.paypal.paypal.error;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class RestRepositoryExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity handleApplicationException(ApplicationException e) {
        return ResponseEntity.status(e.getCustomError().getCode()).body(e.getCustomError());
    }


    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity handle(Exception e, Locale locale) {
        return ResponseEntity.status(400).body(new CustomError(400, e.getMessage(), "this is the cause that the user will see"));
    }
}