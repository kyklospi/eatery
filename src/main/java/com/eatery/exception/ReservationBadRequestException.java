package com.eatery.exception;

import java.time.LocalDateTime;

public class ReservationBadRequestException extends RuntimeException {
    public ReservationBadRequestException(LocalDateTime dateTime) {
        super("Reservation date time must be later than 1 day and within opening hours: " + dateTime);
    }

    public ReservationBadRequestException(int guestNumber) {
        super("Too many guest number " + guestNumber);
    }
}
