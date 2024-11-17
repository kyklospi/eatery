package com.eatery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    private @Id @GeneratedValue Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "eatery_id", referencedColumnName = "id")
    private Eatery eatery;

    @Transient
    private LocalDateTime reservationDateTime;
    private int guestNumber;
    private Status status;

    public enum Status {
        CONFIRMED,
        COMPLETED,
        CANCELLED
    }

    public Reservation(Customer customer, Eatery eatery, LocalDateTime reservationDateTime, int guestNumber) {
        this.customer = customer;
        this.eatery = eatery;
        this.reservationDateTime = reservationDateTime;
        this.guestNumber = guestNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        return guestNumber == that.guestNumber && Objects.equals(id, that.id) && Objects.equals(customer, that.customer) &&
                Objects.equals(eatery, that.eatery) && Objects.equals(reservationDateTime, that.reservationDateTime) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, eatery, reservationDateTime, guestNumber, status);
    }

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