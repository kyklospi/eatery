package com.eatery.validator;

import com.eatery.entity.Eatery;

import java.time.LocalDateTime;

/**
 * Strategy pattern implementation based on eatery business time
 */
public class EateryBusinessTimeStrategy implements ReservationStrategy {
    /**
     * Check if eatery is open at reservation time
     * @param eatery eatery to be reserved
     * @param reservationTime time of reservation
     * @param guestNumber guest number for the reservation
     * @return true if eatery is open at reservation time
     */
    @Override
    public boolean isReservable(Eatery eatery, LocalDateTime reservationTime, int guestNumber) {
        return eatery.getBusinessDayTimes().stream()
                .anyMatch(eateryBusinessDayTime ->
                                reservationTime.getDayOfWeek().equals( eateryBusinessDayTime.openDay()) &&
                                reservationTime.toLocalTime().isAfter(eateryBusinessDayTime.openTime()) &&
                                reservationTime.toLocalTime().isBefore(eateryBusinessDayTime.closeTime())
                );
    }
}
