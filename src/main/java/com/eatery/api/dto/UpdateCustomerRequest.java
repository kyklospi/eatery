package com.eatery.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) used for updating customer information.
 * This class captures the data that can be modified in a customer update request.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private String password;
}
