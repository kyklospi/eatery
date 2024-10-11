package com.favourite.eatery.controller;

import com.favourite.eatery.dto.UpdateReservationRequest;
import com.favourite.eatery.exception.ReservationBadRequestException;
import com.favourite.eatery.exception.ReservationNotFoundException;
import com.favourite.eatery.model.Reservation;
import com.favourite.eatery.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/reservations")
public class ReservationController {
    @Autowired
    private ReservationRepository repository;

    private final LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Reservation> getAll() {
        return repository.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Reservation create(@RequestBody Reservation newReservation) {
        LocalDateTime reservationTime = newReservation.getReservationDateTime();
        boolean fullyBooked = !newReservation.getEatery().isBookable(reservationTime, newReservation.getPersonNumber());

        if (reservationTime.isBefore(tomorrow)) {
            throw new ReservationBadRequestException(newReservation.getReservationDateTime());
        }
        if (fullyBooked) {
            throw new ReservationBadRequestException(newReservation.getPersonNumber());
        }
        newReservation.setStatus(Reservation.Status.CONFIRMED);
        return repository.save(newReservation);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Reservation get(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Reservation replace(@RequestBody UpdateReservationRequest updateReservation, @PathVariable Long id) {
        return repository.findById(id)
                .map(reservation -> {
                    if (updateReservation.getDateTime().isBefore(tomorrow)) {
                        throw new ReservationBadRequestException(updateReservation.getDateTime());
                    }

                    boolean fullyBooked = !reservation.getEatery().isBookable(updateReservation.getDateTime(), updateReservation.getPersonNumber());
                    if (fullyBooked) {
                        throw new ReservationBadRequestException(reservation.getPersonNumber());
                    }

                    reservation.setReservationDateTime(updateReservation.getDateTime());
                    reservation.setPersonNumber(updateReservation.getPersonNumber());
                    reservation.setStatus(Reservation.Status.CONFIRMED);
                    return repository.save(reservation);
                })
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    @PutMapping(path = "/{id}/complete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Reservation replace(@PathVariable Long id) {
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        if (reservation.getReservationDateTime().isBefore(LocalDateTime.now()) &&
                reservation.getStatus().equals(Reservation.Status.CONFIRMED)) {

            reservation.setStatus(Reservation.Status.COMPLETED);
            return repository.save(reservation);
        }
        return reservation;
    }

    @DeleteMapping(path = "/{id}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    void cancel(@PathVariable Long id) {
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        if (reservation.getStatus().equals(Reservation.Status.CONFIRMED)) {
            reservation.setStatus(Reservation.Status.CANCELLED);
            repository.save(reservation);
        }
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        if (reservation.getStatus().equals(Reservation.Status.COMPLETED) ||
                reservation.getStatus().equals(Reservation.Status.CANCELLED)) {

            repository.deleteById(id);
        }
    }
}
