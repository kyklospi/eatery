package com.eatery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for EateryManager-related exceptions.
 * This class handles exceptions related to EateryManager and sends the appropriate HTTP responses.
 */
@RestControllerAdvice
public class EateryManagerExceptionHandler extends RuntimeException {
    @ExceptionHandler(EateryManagerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handle(EateryManagerNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(EateryManagerBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handle(EateryManagerBadRequestException e) {
        return e.getMessage();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String handle(RuntimeException e) {
        return e.getMessage();
    }
}
