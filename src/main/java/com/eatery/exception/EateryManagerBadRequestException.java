package com.eatery.exception;

/**
 * Custom exception class for handling bad request errors related to EateryManager.
 * This exception is thrown when there is an invalid or erroneous request involving an EateryManager.
 */
public class EateryManagerBadRequestException extends RuntimeException {
    public EateryManagerBadRequestException(String message) {
        super(message);
    }
}
