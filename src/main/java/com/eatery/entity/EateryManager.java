package com.eatery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Composite pattern child class
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class EateryManager extends AppUser {
    @Column(unique = true, nullable = false)
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    private long eateryId;

    public EateryManager(String firstName, String lastName, String email, String phoneNumber) {
        super(firstName, lastName, email, phoneNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EateryManager that)) return false;
        return eateryId == that.eateryId && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eateryId);
    }

    @Override
    public String toString() {
        return "EateryManager{" +
                "id=" + this.getId() +
                ", firstName='" + this.getFirstName() + '\'' +
                ", lastName='" + this.getLastName() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", phoneNumber='" + this.getPhoneNumber() + '\'' +
                "eateryId=" + eateryId +
                '}';
    }
}
