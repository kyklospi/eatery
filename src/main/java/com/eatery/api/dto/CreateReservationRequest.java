package com.eatery.api.dto;

import com.eatery.entity.Customer;
import com.eatery.entity.Eatery;
import com.eatery.entity.Reservation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class CreateReservationRequest {
    @NonNull
    private Customer customer;
    @NonNull
    private Eatery eatery;
    @NonNull
    private LocalDateTime reservationDateTime;
    @Schema(minimum = "2")
    private int guestNumber;
    private Reservation.Status status;
}
