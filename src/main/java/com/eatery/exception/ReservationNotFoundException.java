package com.eatery.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException() {
        super("Could not find reservation");
    }
}
