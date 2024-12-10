package com.eatery.exception;

/**
 * Exception thrown when a customer-related request is invalid or malformed.
 * This class extends RuntimeException, meaning it's an unchecked exception.
 */
public class CustomerBadRequestException extends RuntimeException {
    /**
     * Constructor that initializes the exception with a custom message.
     * @param message The error message that provides details about the exception
     */
    public CustomerBadRequestException(String message) {
        super(message);
    }// Pass the message to the superclass (RuntimeException)
}
