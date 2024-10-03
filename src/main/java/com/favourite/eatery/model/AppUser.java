package com.favourite.eatery.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppUser {
    private @Id
    @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    @ElementCollection
    private List<Eatery> favouriteEateries;
    @ElementCollection
    private List<Reservation> reservations;

    public AppUser(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppUser appUser)) return false;
        return Objects.equals(id, appUser.id) && Objects.equals(firstName, appUser.firstName) &&
                Objects.equals(lastName, appUser.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", favouriteEateries=" + favouriteEateries +
                ", reservations=" + reservations +
                '}';
    }
}
