package com.eatery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a generic application user.
 * This class serves as a parent entity in a composite pattern
 * and defines the core properties and behavior of an application user.
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

    /**
     * Parameterized constructor to initialize an AppUser object.
     *
     * @param firstName  First name of the user.
     * @param lastName   Last name of the user.
     * @param email      Email address of the user.
     * @param phoneNumber Phone number of the user.
     */
    public AppUser(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
