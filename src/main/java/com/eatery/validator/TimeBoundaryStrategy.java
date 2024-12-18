package com.eatery.validator;

import com.eatery.entity.Eatery;

import java.time.LocalDateTime;

/**
 * Strategy pattern implementation based on time boundary for reservation
 */
public class TimeBoundaryStrategy implements ReservationStrategy {
    /**
     * Check if reservation time is after time boundary
     * @param eatery eatery to be reserved
     * @param reservationTime time of reservation
     * @param guestNumber guest number for the reservation
     * @return true if reservation is for the day after tomorrow
     */
    @Override
    public boolean isReservable(Eatery eatery, LocalDateTime reservationTime, int guestNumber) {
        final LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        return reservationTime.isAfter(tomorrow);
    }
}
