package com.eatery.api.dto;

import com.eatery.entity.Reservation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) used for creating a new reservation.
 * This class captures the data necessary for making a reservation request.
 */
@Data
public class CreateReservationRequest {
    private long customerId;
    private long eateryId;
    @NonNull
    private LocalDateTime reservationDateTime;
    @Schema(minimum = "2")
    private int guestNumber;
    private Reservation.Status status;
}
