package com.eatery.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) used for updating an existing reservation.
 * This class captures the data that can be modified in a reservation request.
 */
@Data
public class UpdateReservationRequest {
    private LocalDateTime dateTime;
    private int guestNumber;
}
