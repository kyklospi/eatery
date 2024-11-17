package com.eatery.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateReservationRequest {
    private LocalDateTime dateTime;
    private int guestNumber;
}
