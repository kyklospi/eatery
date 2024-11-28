package com.eatery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Composite pattern parent class
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class AppUser {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public AppUser(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
