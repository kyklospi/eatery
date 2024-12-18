package com.eatery.exception;

/**
 * Custom exception to indicate bad requests for reservations.
 * This exception is thrown when a reservation request does not meet the necessary criteria.
 */
public class ReservationBadRequestException extends RuntimeException {
    public ReservationBadRequestException(String s) {
        super("Invalid request parameter " + s);
    }

    public ReservationBadRequestException() {
        super("Reservation date time must be within opening hours, later than tomorrow and within eatery guest capacity");
    }
}
