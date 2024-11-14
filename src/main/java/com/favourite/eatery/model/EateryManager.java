package com.favourite.eatery.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EateryManager extends AppUser {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "eatery_id", referencedColumnName = "id")
    private Eatery eatery;

    public EateryManager(String firstName, String lastName, String email, String phoneNumber) {
        super(firstName, lastName, email, phoneNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EateryManager that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(eatery, that.eatery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), eatery);
    }

    @Override
    public String toString() {
        return "EateryManager{" +
                "id=" + this.getId() +
                ", firstName='" + this.getFirstName() + '\'' +
                ", lastName='" + this.getLastName() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", phoneNumber='" + this.getPhoneNumber() + '\'' +
                "eatery=" + eatery +
                '}';
    }
}
