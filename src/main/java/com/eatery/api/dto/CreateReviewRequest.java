package com.eatery.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) used for creating a new review.
 * This class captures the data necessary for making a review create request.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewRequest {
    private long eateryId;
    private long customerId;
    private String message;
    private int rating;
}
