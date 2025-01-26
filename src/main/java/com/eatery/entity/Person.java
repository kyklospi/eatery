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
public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;

    /**
     * Parameterized constructor to initialize an AppUser object.
     *
     * @param firstName  First name of the user.
     * @param lastName   Last name of the user.
     * @param email      Email address of the user.
     * @param phoneNumber Phone number of the user.
     */
    public Person(String firstName, String lastName, String email, String phoneNumber, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
    }
}
