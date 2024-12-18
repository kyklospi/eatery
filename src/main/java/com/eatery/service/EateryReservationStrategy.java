package com.eatery.service;

public class EateryReservationStrategy implements ReservationStrategy {

    /**
     * Strategy pattern implementation
     * Check availability of Eatery by using strategy pattern Reservable
     * @param isOpen if eatery is open at reservation time
     * @param isFullyBooked if eatery is fully booked
     * @param isValidReservationTime if reservation is made before reservation deadline
     */
    @Override
    public boolean isReservable(boolean isValidReservationTime, boolean isOpen, boolean isFullyBooked) {
        return isOpen && isValidReservationTime && !isFullyBooked;
    }
}
