package com.eatery.exception;

import java.time.LocalDateTime;

/**
 * Custom exception to indicate bad requests for reservations.
 * This exception is thrown when a reservation request does not meet the necessary criteria.
 */
public class ReservationBadRequestException extends RuntimeException {
    public ReservationBadRequestException(String s) {
        super("Invalid request parameter " + s);
    }

    public ReservationBadRequestException(LocalDateTime dateTime) {
        super("Reservation date time " + dateTime.toString() + " must be later than tomorrow and within opening hours");
    }

    public ReservationBadRequestException(int guestNumber) {
        super("Too many guest number " + guestNumber);
    }
}
