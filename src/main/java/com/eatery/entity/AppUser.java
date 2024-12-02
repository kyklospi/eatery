package com.eatery.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Entity class representing a generic application user.
 * This class serves as a parent entity in a composite pattern
 * and defines the core properties and behavior of an application user.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppUser {
    /**
     * Unique identifier for the user.
     * This field is automatically generated.
     */
    private @Id
    @GeneratedValue Long id;
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

    /**
     * Overrides the default equals method to compare AppUser objects based on their properties.
     *
     * @param o The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppUser appUser)) return false;
        return Objects.equals(id, appUser.id) && Objects.equals(firstName, appUser.firstName) &&
                Objects.equals(lastName, appUser.lastName) && Objects.equals(email, appUser.email) &&
                Objects.equals(phoneNumber, appUser.phoneNumber);
    }

    /**
     * Overrides the default hashCode method to generate a hash based on the user's properties.
     *
     * @return The hash code of the AppUser object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phoneNumber);
    }
}
