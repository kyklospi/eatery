package com.eatery.validator;

import com.eatery.entity.Eatery;

import java.time.LocalDateTime;

/**
 * Strategy pattern interface class
 */
public interface ReservationStrategy {
    boolean isReservable(Eatery eatery, LocalDateTime reservationTime, int guestNumber);
}
