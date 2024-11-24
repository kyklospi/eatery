package com.eatery.api.dto;

import com.eatery.entity.BusinessDayTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEateryRequest {
    private String type;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private int guestCapacity;
    private Set<BusinessDayTime> businessDayTimes;
}
