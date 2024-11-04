package com.favourite.eatery.service;

import com.favourite.eatery.dto.CreateReservationRequest;
import com.favourite.eatery.dto.UpdateReservationRequest;
import com.favourite.eatery.exception.ReservationBadRequestException;
import com.favourite.eatery.exception.ReservationNotFoundException;
import com.favourite.eatery.model.Reservation;
import com.favourite.eatery.notification.NotificationHandler;
import com.favourite.eatery.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.favourite.eatery.model.Reservation.Status.CANCELLED;
import static com.favourite.eatery.model.Reservation.Status.CONFIRMED;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;

    private final LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public Reservation get(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    public Reservation create(CreateReservationRequest reservationRequest) {
        LocalDateTime reservationTime = reservationRequest.getReservationDateTime();
        boolean fullyBooked = reservationRequest.getEatery().isFullyBooked(reservationTime, reservationRequest.getPersonNumber());

        if (reservationTime.isBefore(tomorrow)) {
            throw new ReservationBadRequestException(reservationRequest.getReservationDateTime());
        }
        if (fullyBooked) {
            throw new ReservationBadRequestException(reservationRequest.getPersonNumber());
        }

        Reservation newReservation = new Reservation(
                reservationRequest.getUser(),
                reservationRequest.getEatery(),
                reservationRequest.getReservationDateTime(),
                reservationRequest.getPersonNumber()
        );
        newReservation.setStatus(CONFIRMED);
        sendReservationSMS(reservationRequest.getUser().getPhoneNumber(), newReservation);
        return reservationRepository.save(newReservation);
    }

    public Reservation replace(UpdateReservationRequest updateReservation, Long id) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    if (updateReservation.getDateTime().isBefore(tomorrow)) {
                        throw new ReservationBadRequestException(updateReservation.getDateTime());
                    }

                    boolean fullyBooked = reservation.getEatery().isFullyBooked(
                            updateReservation.getDateTime(), updateReservation.getPersonNumber()
                    );
                    if (fullyBooked) {
                        throw new ReservationBadRequestException(reservation.getPersonNumber());
                    }

                    reservation.setReservationDateTime(updateReservation.getDateTime());
                    reservation.setPersonNumber(updateReservation.getPersonNumber());
                    reservation.setStatus(CONFIRMED);
                    sendReservationSMS(reservation.getUser().getPhoneNumber(), reservation);
                    return reservationRepository.save(reservation);
                })
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    public Reservation complete(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        if (reservation.getReservationDateTime().isBefore(LocalDateTime.now()) &&
                reservation.getStatus().equals(CONFIRMED)) {

            reservation.setStatus(Reservation.Status.COMPLETED);
            return reservationRepository.save(reservation);
        }
        return reservation;
    }

    public Reservation cancel(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        if (reservation.getStatus().equals(CONFIRMED)) {
            reservation.setStatus(CANCELLED);
            reservationRepository.save(reservation);
        }
        sendReservationSMS(reservation.getUser().getPhoneNumber(), reservation);
        return reservation;
    }

    public void delete(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        if (reservation.getStatus().equals(Reservation.Status.COMPLETED) ||
                reservation.getStatus().equals(CANCELLED)) {

            reservationRepository.deleteById(id);
        }
    }

    private void sendReservationSMS(String userPhoneNumber, Reservation reservation) {
        String prefixText = getReservationStatusMessage(reservation);
        switch (reservation.getStatus()) {
            case CONFIRMED ->
                    NotificationHandler.sendSMS(
                            userPhoneNumber,
                            prefixText + CONFIRMED
                    );
            case CANCELLED ->
                    NotificationHandler.sendSMS(
                            userPhoneNumber,
                            prefixText + CANCELLED
                    );
            case COMPLETED -> {}
        }
    }

    private static String getReservationStatusMessage(Reservation reservation) {
        LocalDateTime reservationDateTime = reservation.getReservationDateTime();

        // Example: Your reservation on Monday, 1 January 2025 at 17:00 for 4 persons is CONFIRMED
        return "Your reservation on " + reservationDateTime.getDayOfWeek() + ", " +
                reservationDateTime.getDayOfMonth() + " " + reservationDateTime.getMonth().getValue() + " " + reservationDateTime.getYear() +
                " at " + reservationDateTime.getHour() + ":" + reservationDateTime.getMinute() +
                " for " + reservation.getPersonNumber() + " persons is ";
    }
}
