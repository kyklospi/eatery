package com.eatery.validator;

import com.eatery.entity.Eatery;
import com.eatery.entity.Reservation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Strategy pattern implementation based on eatery guest capacity
 */
public class EateryCapacityStrategy implements ReservationStrategy {
    /**
     * Checks if eatery has space for guest number from 2 hours before reservation time until 2 hours after reservation time
     * @param eatery eatery to be booked
     * @param reservationTime time of reservation
     * @param guestNumber guest number for the reservation
     * @return true when eatery has space for guest number
     */
    @Override
    public boolean isReservable(Eatery eatery, LocalDateTime reservationTime, int guestNumber) {
        return !isFullyBooked(eatery, reservationTime, guestNumber);
    }

    private boolean isFullyBooked(Eatery eatery, LocalDateTime atTime, int newGuestNumber) {
        List<Reservation> confirmedReservationsAtDuration = getConfirmedReservationsAtTimeSlot(eatery, atTime.minusHours(2), atTime.plusHours(2));
        int totalGuestNumberAtDuration = countGuestNumber(confirmedReservationsAtDuration);

        return (totalGuestNumberAtDuration + newGuestNumber) > eatery.getGuestCapacity();
    }

    private List<Reservation> getConfirmedReservationsAtTimeSlot(Eatery eatery, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return eatery.getReservationList().stream()
                .filter(eateryReservation -> eateryReservation.getReservationDateTime().isAfter(startDateTime) &&
                        eateryReservation.getReservationDateTime().isBefore(endDateTime) &&
                        eateryReservation.getStatus().equals(Reservation.Status.CONFIRMED)
                )
                .toList();

    }

    private int countGuestNumber(List<Reservation> reservations) {
        int sum = 0;
        for (Reservation reservation : reservations) {
            sum += reservation.getGuestNumber();
        }
        return sum;
    }

}
