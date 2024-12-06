package com.eatery.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    private @Id @GeneratedValue Long id;
    private long customerId;
    private long eateryId;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime reservationDateTime;

    private int guestNumber;
    private Status status;

    public enum Status {
        CONFIRMED,
        COMPLETED,
        CANCELLED
    }

    public Reservation(long customerId, long eateryId, LocalDateTime reservationDateTime, int guestNumber) {
        this.guestNumber = guestNumber;
        this.reservationDateTime = reservationDateTime;
        this.eateryId = eateryId;
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        return customerId == that.customerId && eateryId == that.eateryId && guestNumber == that.guestNumber &&
                Objects.equals(id, that.id) && Objects.equals(reservationDateTime, that.reservationDateTime) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, eateryId, reservationDateTime, guestNumber, status);
    }

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
