package com.eatery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Column(unique = true, nullable = false)
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private long eateryId;
    private long customerId;
    private String message;
    private int rating;

    public Review(long eateryId, long customerId, String message, int rating) {
        this.eateryId = eateryId;
        this.customerId = customerId;
        this.message = message;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", eateryId=" + eateryId +
                ", customerId=" + customerId +
                ", message='" + message + '\'' +
                ", rating=" + rating +
                '}';
    }
}
