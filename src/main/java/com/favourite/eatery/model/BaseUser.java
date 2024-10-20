package com.favourite.eatery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BaseUser {
    private @Id
    @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public BaseUser(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseUser baseUser)) return false;
        return Objects.equals(id, baseUser.id) && Objects.equals(firstName, baseUser.firstName) &&
                Objects.equals(lastName, baseUser.lastName) && Objects.equals(email, baseUser.email) &&
                Objects.equals(phoneNumber, baseUser.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phoneNumber);
    }
}
