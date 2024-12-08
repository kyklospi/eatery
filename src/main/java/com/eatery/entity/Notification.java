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
public class Notification {
    @Column(unique = true, nullable = false)
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    private Long customerId;
    private Long reservationId;
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date createdAt;

    public Notification(Long customerId, Long reservationId, String message) {
        this.customerId = customerId;
        this.reservationId = reservationId;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(customerId, that.customerId) && Objects.equals(reservationId, that.reservationId) && Objects.equals(message, that.message) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, reservationId, message, createdAt);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", reservationId=" + reservationId +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
