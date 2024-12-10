package com.eatery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for handling Eatery-related exceptions.
 * This class catches exceptions thrown by controllers and returns appropriate HTTP responses.
 */
@RestControllerAdvice
public class EateryExceptionHandler {
    @ExceptionHandler(EateryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handle(EateryNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String handle(RuntimeException e) {
        return e.getMessage();
    }
}
