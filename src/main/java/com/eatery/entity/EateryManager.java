package com.eatery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.Set;

/**
 * Represents the manager of an eatery.
 * This class is a child of the `AppUser` class and is part of the composite pattern.
 * The `EateryManager` manages an `Eatery` and has the same attributes as a regular user, such as first name, last name, email, and phone number.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class EateryManager extends Person {
    @Column(unique = true, nullable = false)
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    /**
     * The eatery managed by this manager.
     */
    private long eateryId;

    private String jobTitle;

    @JdbcTypeCode(SqlTypes.JSON)
    private Set<BusinessDayTime> workSchedules = Set.of();

    /**
     * Constructor to create an `EateryManager` with the given user details.
     * @param firstName  The first name of the manager.
     * @param lastName   The last name of the manager.
     * @param eateryId The eatery id associated with the manager
     * @param jobTitle The job title of the manager
     * @param workSchedules The work schedule of the manager
     */
    public EateryManager(
            String firstName,
            String lastName,
            String username,
            String password,
            long eateryId,
            String jobTitle,
            Set<BusinessDayTime> workSchedules
    ) {
        super(firstName, lastName, username, password);
        this.eateryId = eateryId;
        this.jobTitle = jobTitle;
        this.workSchedules = workSchedules;
    }

    /**
     * Compares this `EateryManager` with another object for equality.
     * Two `EateryManager` objects are considered equal if their attributes (including `eatery`) are equal.
     * @param o The object to compare this instance with.
     * @return `true` if both objects are equal, `false` otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EateryManager that)) return false;
        return eateryId == that.eateryId && Objects.equals(id, that.id);
    }

    /**
     * Generates a hash code for this `EateryManager` based on its attributes.
     * The hash code is computed using the attributes of the `AppUser` superclass and the `eatery` field.
     * @return The hash code of this `EateryManager`.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, eateryId);
    }

    /**
     * Returns a string representation of this `EateryManager`.
     * The string contains details about the manager and the managed eatery.
     * @return A string representation of this `EateryManager`.
     */
    @Override
    public String toString() {
        return "EateryManager{" +
                "id=" + this.getId() +
                ", firstName='" + this.getFirstName() + '\'' +
                ", lastName='" + this.getLastName() + '\'' +
                ", username='" + this.getUsername() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", eateryId=" + eateryId + '\'' +
                ", jobTitle=" + jobTitle + '\'' +
                ", workSchedule=" + workSchedules +
                '}';
    }
}
