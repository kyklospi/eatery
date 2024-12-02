package com.eatery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a reservation made by a customer at an eatery.
 * The reservation includes details such as the customer, eatery, reservation time, guest number, and status.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    /**
     * Unique identifier for the reservation.
     */
    private @Id @GeneratedValue Long id;

    /**
     * The customer who made the reservation.
     * Many reservations can belong to the same customer.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    /**
     * The eatery where the reservation was made.
     * Many reservations can belong to the same eatery.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "eatery_id", referencedColumnName = "id")
    private Eatery eatery;

    /**
     * The date and time when the reservation was made.
     * This field is not stored in the database, marked with `@Transient`.
     */
    @Transient
    private LocalDateTime reservationDateTime;
    private int guestNumber;
    private Status status;

    public enum Status {
        CONFIRMED,
        COMPLETED,
        CANCELLED
    }

    /**
     * Constructor to create a new reservation with the given details.
     * @param customer The customer who made the reservation.
     * @param eatery   The eatery where the reservation was made.
     * @param reservationDateTime The date and time when the reservation was made.
     * @param guestNumber The number of guests for the reservation.
     */
    public Reservation(Customer customer, Eatery eatery, LocalDateTime reservationDateTime, int guestNumber) {
        this.customer = customer;
        this.eatery = eatery;
        this.reservationDateTime = reservationDateTime;
        this.guestNumber = guestNumber;
    }

    /**
     * Compares this reservation with another object for equality.
     * Two reservations are considered equal if their attributes (including customer, eatery, reservation time, guest number, and status) are equal.
     * @param o The object to compare this instance with.
     * @return `true` if both objects are equal, `false` otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        return guestNumber == that.guestNumber && Objects.equals(id, that.id) && Objects.equals(customer, that.customer) &&
                Objects.equals(eatery, that.eatery) && Objects.equals(reservationDateTime, that.reservationDateTime) &&
                status == that.status;
    }

    /**
     * Generates a hash code for this reservation based on its attributes.
     * The hash code is computed using the `id`, `customer`, `eatery`, `reservationDateTime`, `guestNumber`, and `status`.
     * @return The hash code of this reservation.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, customer, eatery, reservationDateTime, guestNumber, status);
    }

    /**
     * Returns a string representation of this reservation.
     * The string contains details about the reservation including the customer, eatery, reservation time, guest number, and status.
     * @return A string representation of this reservation.
     */
    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", customer=" + customer +
                ", eatery=" + eatery +
                ", reservationDateTime=" + reservationDateTime +
                ", guestNumber=" + guestNumber +
                ", status=" + status +
                '}';
    }
}
