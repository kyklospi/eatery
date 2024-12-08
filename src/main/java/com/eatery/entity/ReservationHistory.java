package com.eatery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ReservationHistory implements Comparable<ReservationHistory> {
    private @Id Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Reservation reservation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date updatedAt;

    public ReservationHistory(Reservation reservation, Date updatedAt) {
        this.reservation = reservation;
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationHistory that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(reservation, that.reservation) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reservation, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "ReservationHistory{" +
                "id=" + id +
                ", reservation=" + reservation +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public int compareTo(ReservationHistory history) {
        // compare by updateAt timestamp
        if (this.getUpdatedAt().compareTo(history.getUpdatedAt()) != 0) {
            return this.getUpdatedAt().compareTo(history.updatedAt);
        }
        // no update yet, then compare by createdAt timestamp
        else {
            return this.createdAt.compareTo(history.createdAt);
        }
    }
}
