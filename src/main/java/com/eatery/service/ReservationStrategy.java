package com.eatery.service;

/**
 * Strategy pattern interface class
 */
public interface ReservationStrategy {
    boolean isReservable(boolean isValidReservationTime, boolean isOpen, boolean isFullyBooked);
}
