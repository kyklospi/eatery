package com.eatery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/**
 * Composite pattern child class
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer extends AppUser {
    @Column(unique = true, nullable = false)
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservations;

    public Customer(String firstName, String lastName, String email, String phoneNumber) {
        super(firstName, lastName, email, phoneNumber);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + this.getId() +
                ", firstName='" + this.getFirstName() + '\'' +
                ", lastName='" + this.getLastName() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", phoneNumber='" + this.getPhoneNumber() + '\'' +
                ", reservations=" + reservations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(reservations, customer.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), reservations);
    }
}
