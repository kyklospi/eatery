package com.eatery.exception;

/**
 * Custom exception to indicate that a reservation was not found.
 * This exception is thrown when the requested reservation cannot be found in the system.
 */
public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(Long id) {
        super("Could not find reservation " + id);
    }
}
