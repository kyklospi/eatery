package com.eatery.service;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReservationContext {
    private ReservationStrategy reservationStrategy;

    public ReservationContext(ReservationStrategy reservationStrategy) {
        this.reservationStrategy = reservationStrategy;
    }

    public boolean isReservable(boolean isValidReservationTime, boolean isOpen, boolean isFullyBooked) {
        return reservationStrategy.isReservable(isValidReservationTime, isOpen, isFullyBooked);
    }
}
