package com.eatery.exception;

/**
 * Custom exception class for handling bad requests related to eateries.
 * This exception is thrown when there is an issue with an eatery request, such as invalid data or request parameters.
 */
public class EateryBadRequestException extends RuntimeException {
    public EateryBadRequestException(String message) {
        super(message);
    }
}
