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
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ReservationHistory implements Comparable<ReservationHistory> {
    @Column(unique = true, nullable = false)
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    private Long reservationId;
    private Long customerId;
    private Long eateryId;

    /**
     * The date and time of the reservation.
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime reservationDateTime;

    private int guestNumber;
    private Reservation.Status status;

    @CreationTimestamp
    private Date timestamp;

    public ReservationHistory(
            Long reservationId,
            Long customerId,
            Long eateryId,
            LocalDateTime reservationDateTime,
            int guestNumber,
            Reservation.Status status
    ) {
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.eateryId = eateryId;
        this.reservationDateTime = reservationDateTime;
        this.guestNumber = guestNumber;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationHistory that)) return false;
        return reservationId.equals(that.reservationId) && customerId.equals(that.customerId) && eateryId.equals(that.eateryId) &&
                guestNumber == that.guestNumber && Objects.equals(id, that.id) &&
                Objects.equals(reservationDateTime, that.reservationDateTime) && status == that.status &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reservationId, customerId, eateryId, reservationDateTime, guestNumber, status, timestamp);
    }

    @Override
    public String toString() {
        return "ReservationHistory{" +
                "id=" + id +
                ", reservationId=" + reservationId +
                ", customerId=" + customerId +
                ", eateryId=" + eateryId +
                ", reservationDateTime=" + reservationDateTime +
                ", guestNumber=" + guestNumber +
                ", status=" + status +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public int compareTo(ReservationHistory history) {
        return this.getTimestamp().compareTo(history.timestamp);
    }
}
