package com.favourite.eatery.controller;

import com.favourite.eatery.dto.CreateReservationRequest;
import com.favourite.eatery.dto.UpdateReservationRequest;
import com.favourite.eatery.model.Reservation;
import com.favourite.eatery.service.ReservationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Reservations not found"),
            @ApiResponse(responseCode = "500", description = "Reservations could not be fetched")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Reservation> getAll() {
        return reservationService.getAll();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Reservation could not be created")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Reservation create(@RequestBody CreateReservationRequest newReservation) {
        return reservationService.create(newReservation);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Reservation could not be fetched")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Reservation get(@PathVariable Long id) {
        return reservationService.get(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Reservation could not be updated")
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Reservation replace(@RequestBody UpdateReservationRequest updateReservation, @PathVariable Long id) {
        return reservationService.replace(updateReservation, id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Reservation could not be set to complete")
    })
    @PutMapping(path = "/{id}/complete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Reservation complete(@PathVariable Long id) {
        return reservationService.complete(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Reservation could not be canceled")
    })
    @DeleteMapping(path = "/{id}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    Reservation cancel(@PathVariable Long id) {
        return reservationService.cancel(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Reservation could not be deleted")
    })
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        reservationService.delete(id);
    }
}
