package com.eatery.api.dto;

import com.eatery.entity.WorkSchedule;
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
    private String email;
    private String phoneNumber;
    private long eateryId;
    private String jobTitle;
    private Set<WorkSchedule> workSchedules;
}
