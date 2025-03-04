package com.eatery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handler for reservation-related exceptions.
 * This class is responsible for handling different exceptions related to reservations
 * and returning appropriate HTTP responses.
 */
@RestControllerAdvice
public class ReservationExceptionHandler {
    @ExceptionHandler(ReservationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handle(ReservationNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ReservationBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handle(ReservationBadRequestException e) {
        return e.getMessage();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String handle(RuntimeException e) {
        return e.getMessage();
    }
}
