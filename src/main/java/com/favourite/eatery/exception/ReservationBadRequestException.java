package com.favourite.eatery.exception;

import java.time.LocalDateTime;

public class ReservationBadRequestException extends RuntimeException {
    public ReservationBadRequestException(LocalDateTime dateTime) {
        super("Invalid reservation date time " + dateTime);
    }
}
