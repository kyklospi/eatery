package com.eatery.exception;

public class CustomerBadRequestException extends RuntimeException {
    public CustomerBadRequestException(String message) {
        super(message);
    }
}
