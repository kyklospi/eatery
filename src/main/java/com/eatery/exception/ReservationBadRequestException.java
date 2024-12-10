package com.eatery.exception;

import java.time.LocalDateTime;

/**
 * Custom exception to indicate bad requests for reservations.
 * This exception is thrown when a reservation request does not meet the necessary criteria.
 */
public class ReservationBadRequestException extends RuntimeException {
    public ReservationBadRequestException(LocalDateTime dateTime) {
        super("Reservation date time must be later than 1 day and within opening hours: " + dateTime);
    }

    public ReservationBadRequestException(int guestNumber) {
        super("Too many guest number " + guestNumber);
    }

    public ReservationBadRequestException(String s) {
        super("Invalid request parameter " + s);
    }
}
