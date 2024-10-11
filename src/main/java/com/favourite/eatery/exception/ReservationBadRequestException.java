package com.favourite.eatery.exception;

import java.time.LocalDateTime;

public class ReservationBadRequestException extends RuntimeException {
    public ReservationBadRequestException(LocalDateTime dateTime) {
        super("Reservation date time must be later than 1 day: " + dateTime);
    }

    public ReservationBadRequestException(int guestNumber) {
        super("Too many guest number " + guestNumber);
    }
}
