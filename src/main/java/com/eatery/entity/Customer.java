package com.eatery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/**
 * Entity representing a Customer in the system.
 * A `Customer` is a specialized type of `AppUser` that includes relationships to
 * their reservations. This class is part of a composite pattern.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer extends Person {
    @Column(unique = true, nullable = false)
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    private String phoneNumber;

    /**
     * List of reservations associated with the customer.
     * This is a one-to-many relationship where the `Reservation` entity contains a `customerId` field
     * mapped to this class.
     */
    @OneToMany(mappedBy = "customerId", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    /**
     * List of reviews associated with the customer.
     * This is a one-to-many relationship where the `Review` entity contains a `customerId` field
     * mapped to this class.
     */
    @OneToMany(mappedBy = "customerId", cascade = CascadeType.ALL)
    private List<Review> reviews;

    /**
     * Constructs a new Customer with the specified details.
     * @param firstName  The first name of the customer.
     * @param lastName   The last name of the customer.
     * @param phoneNumber The phone number of the customer.
     */
    public Customer(
            String firstName,
            String lastName,
            String username,
            String password,
            String phoneNumber
    ) {
        super(firstName, lastName, username, password);
        this.phoneNumber = phoneNumber;
    }

    /**
     * Overrides the default `toString` method to provide a detailed string representation of a `Customer` instance.
     * @return A string containing the customer's ID, name, email, phone number, and reservations.
     */
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + this.getId() +
                ", firstName='" + this.getFirstName() + '\'' +
                ", lastName='" + this.getLastName() + '\'' +
                ", username='" + this.getUsername() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", reservations=" + reservations + '\'' +
                ", reviews=" + reviews +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(reservations, customer.reservations);
    }
}
