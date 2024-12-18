package com.eatery.validator;

import com.eatery.entity.Eatery;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ReservationContext {
    private ReservationStrategy reservationStrategy;

    public ReservationContext(ReservationStrategy reservationStrategy) {
        this.reservationStrategy = reservationStrategy;
    }

    public boolean isReservable(Eatery eatery, LocalDateTime reservationTime, int guestNumber) {
        return reservationStrategy.isReservable(eatery, reservationTime, guestNumber);
    }
}
