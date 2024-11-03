package com.favourite.eatery.exception;

public class EateryBadRequestException extends RuntimeException {
    public EateryBadRequestException(String message) {
        super(message);
    }

    public EateryBadRequestException(Long eateryId) {
        super("Bad request for Eatery with ID: " + eateryId);
    }
}