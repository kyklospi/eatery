package com.eatery.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reservation {
    /**
     * Unique identifier for the reservation.
     */
    @Column(unique = true, nullable = false)
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    /**
     * The customer who made the reservation.
     * Many reservations can belong to the same customer.
     */
    private Long customerId;

    /**
     * The eatery where the reservation was made.
     * Many reservations can belong to the same eatery.
     */
    private Long eateryId;

    /**
     * The date and time of the reservation.
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime reservationDateTime;

    private int guestNumber;
    private Status status;

    public enum Status {
        CONFIRMED,
        COMPLETED,
        CANCELLED,
        DELETED
    }

    /**
     * Constructor to create a new reservation with the given details.
     * @param customerId The customer who made the reservation.
     * @param eateryId   The eatery where the reservation was made.
     * @param reservationDateTime The date and time when the reservation was made.
     * @param guestNumber The number of guests for the reservation.
     */
    public Reservation(Long customerId, Long eateryId, LocalDateTime reservationDateTime, int guestNumber) {
        this.guestNumber = guestNumber;
        this.reservationDateTime = reservationDateTime;
        this.eateryId = eateryId;
        this.customerId = customerId;
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
        return customerId.equals(that.customerId) && eateryId.equals(that.eateryId) && guestNumber == that.guestNumber &&
                Objects.equals(id, that.id) && Objects.equals(reservationDateTime, that.reservationDateTime) &&
                status == that.status;
    }

    /**
     * Generates a hash code for this reservation based on its attributes.
     * The hash code is computed using the `id`, `customer`, `eatery`, `reservationDateTime`, `guestNumber`, and `status`.
     * @return The hash code of this reservation.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, eateryId, reservationDateTime, guestNumber, status);
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
                ", customerId=" + customerId +
                ", eateryId=" + eateryId +
                ", reservationDateTime=" + reservationDateTime +
                ", guestNumber=" + guestNumber +
                ", status=" + status +
                '}';
    }
}
