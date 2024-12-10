package com.eatery.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) used for updating user information.
 * This class captures the data that can be modified in a user update request.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
