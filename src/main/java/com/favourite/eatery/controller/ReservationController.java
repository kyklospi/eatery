package com.favourite.eatery.controller;

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
        if (reservationTime.isAfter(tomorrow) &&
                newReservation.getEatery().isBookable(reservationTime, newReservation.getPersonNumber()))
        {
            newReservation.setStatus(Reservation.Status.CONFIRMED);
            return repository.save(newReservation);
        }
        throw new ReservationBadRequestException(newReservation.getReservationDateTime());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Reservation get(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Reservation replace(@RequestBody LocalDateTime newDateTime, @PathVariable Long id) {
        return repository.findById(id)
                .map(reservation -> {
                    if (newDateTime.isAfter(tomorrow) &&
                            reservation.getEatery().isBookable(newDateTime, reservation.getPersonNumber()))
                    {
                        reservation.setReservationDateTime(newDateTime);
                        reservation.setStatus(Reservation.Status.CONFIRMED);
                        return repository.save(reservation);
                    }
                    throw new ReservationBadRequestException(newDateTime);
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
