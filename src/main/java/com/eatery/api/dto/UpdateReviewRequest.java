package com.eatery.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) used for updating review information.
 * This class captures the data that can be modified in a review update request.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReviewRequest {
    private String message;
    private int rating;
}
