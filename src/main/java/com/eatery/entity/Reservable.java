package com.eatery.entity;

import java.time.LocalDateTime;

/**
 * Strategy pattern interface class
 */
public interface Reservable {
    boolean isOpenAt(LocalDateTime reservationTime);
    boolean isFullyBooked(LocalDateTime reservationTime, int guestNumber);
}
