package com.eatery.api.dto;

import com.eatery.entity.BusinessDayTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Data Transfer Object (DTO) used for updating eatery manager information.
 * This class captures the data that can be modified in a manager update request.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateManagerRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private long eateryId;
    private String jobTitle;
    private Set<BusinessDayTime> workSchedules;
}
