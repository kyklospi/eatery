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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Reservation> getAll() {
        return repository.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Reservation create(@RequestBody Reservation newReservation) {
        if (newReservation.getReservationDateTime().isBefore(LocalDateTime.now())) {
            throw new ReservationBadRequestException(newReservation.getReservationDateTime());
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
    Reservation replace(@RequestBody LocalDateTime newDateTime, @PathVariable Long id) {
        return repository.findById(id)
                .map(reservation -> {
                    reservation.setReservationDateTime(newDateTime);
                    reservation.setStatus(Reservation.Status.CONFIRMED);
                    return repository.save(reservation);
                })
                .orElseThrow(() -> new ReservationNotFoundException(id));
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
}
