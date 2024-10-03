package com.favourite.eatery.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    private @Id
    @GeneratedValue Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "eatery_id", referencedColumnName = "id")
    private Eatery eatery;
    @Transient
    private DayOfWeek day;
    @Transient
    private LocalTime startTime;
    @Transient
    private LocalTime endTime;
    private Status status;

    enum Status {
        CONFIRMED,
        COMPLETED, //
        CANCELLED
    }

    public Reservation(AppUser user, Eatery eatery, DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.user = user;
        this.eatery = eatery;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(eatery, that.eatery) &&
                day == that.day && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, eatery, day, startTime, endTime, status);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user=" + user +
                ", eatery=" + eatery +
                ", day=" + day +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                '}';
    }
}
