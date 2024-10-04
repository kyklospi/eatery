package com.favourite.eatery.model;

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
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "eatery_id", referencedColumnName = "id")
    private Eatery eatery;

    @Transient
    private LocalDateTime reservationDateTime;
    private Status status;

    public enum Status {
        CONFIRMED,
        COMPLETED, //
        CANCELLED
    }

    public Reservation(AppUser user, Eatery eatery, LocalDateTime reservationDateTime) {
        this.user = user;
        this.eatery = eatery;
        this.reservationDateTime = reservationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(eatery, that.eatery) &&
                Objects.equals(reservationDateTime, that.reservationDateTime) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, eatery, reservationDateTime, status);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user=" + user +
                ", eatery=" + eatery +
                ", reservationDateTime=" + reservationDateTime +
                ", status=" + status +
                '}';
    }
}
