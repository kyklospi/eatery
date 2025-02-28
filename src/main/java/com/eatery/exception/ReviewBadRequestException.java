package com.eatery.exception;

/**
 * Exception thrown when a review-related request is invalid or malformed.
 * This class extends RuntimeException, meaning it's an unchecked exception.
 */
public class ReviewBadRequestException extends RuntimeException {
    /**
     * Constructor that initializes the exception with a custom message.
     * @param message The error message that provides details about the exception
     */
    public ReviewBadRequestException(String message) {
        super(message);
    }// Pass the message to the superclass (RuntimeException)
}
