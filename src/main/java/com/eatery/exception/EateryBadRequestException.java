package com.eatery.exception;

public class EateryBadRequestException extends RuntimeException {
    public EateryBadRequestException(String message) {
        super(message);
    }
}
