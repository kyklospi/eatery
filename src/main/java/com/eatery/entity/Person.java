package com.eatery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a generic person.
 * This class serves as a parent entity in a composite pattern
 * and defines the core properties of a person.
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class Person {
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    /**
     * Parameterized constructor to initialize an Person object.
     *
     * @param firstName First name of the person.
     * @param lastName Last name of the person.
     * @param username User name of the person.
     * @param password Password of the person.
     */
    public Person(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }
}
