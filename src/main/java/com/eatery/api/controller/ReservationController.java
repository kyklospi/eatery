package com.eatery.api.controller;

import com.eatery.api.dto.CreateReservationRequest;
import com.eatery.api.dto.UpdateReservationRequest;
import com.eatery.entity.Reservation;
import com.eatery.service.ReservationService;
import com.eatery.entity.ReservationHistory;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    /**
     * Fetches all reservations from the database.
     * @return A list of all reservations.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned"),
            @ApiResponse(responseCode = "404", description = "Reservations not found"),
            @ApiResponse(responseCode = "500", description = "Reservations could not be fetched")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Reservation> getAll() {
        return reservationService.getAll();
    }

    /**
     * Creates a new reservation based on the provided request data.
     * @param newReservation The data for the new reservation.
     * @return The created reservation.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Reservation could not be created")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Reservation create(@RequestBody CreateReservationRequest newReservation) {
        return reservationService.create(newReservation);
    }

    /**
     * Fetches a specific reservation by its ID.
     * @param id The ID of the reservation to retrieve.
     * @return The reservation with the specified ID.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Reservation could not be fetched")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Reservation get(@PathVariable Long id) {
        return reservationService.get(id);
    }

    /**
     * Updates an existing reservation with the provided updated details.
     * @param updateReservation The new details for the reservation.
     * @param id The ID of the reservation to update.
     * @return The updated reservation.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully modified"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Reservation could not be updated")
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Reservation replace(@RequestBody UpdateReservationRequest updateReservation, @PathVariable Long id) {
        return reservationService.replace(updateReservation, id);
    }

    /**
     * Marks the reservation as completed.
     * @param id The ID of the reservation to mark as completed.
     * @return The updated reservation after it is marked as completed.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully modified"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Reservation could not be set to complete")
    })
    @PutMapping(path = "/{id}/complete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Reservation complete(@PathVariable Long id) {
        return reservationService.complete(id);
    }

    /**
     * Cancels a reservation.
     * @param id The ID of the reservation to cancel.
     * @return The reservation that was canceled.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully modified"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Reservation could not be canceled")
    })
    @PutMapping(path = "/{id}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    Reservation cancel(@PathVariable Long id) {
        return reservationService.cancel(id);
    }

    /**
     * Deletes a reservation by its ID.
     * @param id The ID of the reservation to delete.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Reservation could not be deleted")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        reservationService.delete(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned"),
            @ApiResponse(responseCode = "500", description = "Reservation history could not be fetched")
    })
    @GetMapping(path = "{id}/history", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ReservationHistory> getHistory(@PathVariable Long id) {
       return reservationService.history(id);
    }
}
