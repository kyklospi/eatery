package com.eatery.exception;

/**
 * Custom exception class for handling scenarios where a customer cannot be found.
 * This exception is thrown when a customer with a specific ID is not found in the system.
 */
public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super("Could not find customer " + id);
    }
}
