package com.eatery.exception;

/**
 * Custom exception class for handling scenarios where a review cannot be found.
 * This exception is thrown when a review with a specific ID is not found in the system.
 */
public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException() {
        super("Could not find review");
    }
}
