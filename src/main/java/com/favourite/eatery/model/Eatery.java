package com.favourite.eatery.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Eatery {
    private @Id @GeneratedValue Long id;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;

    @ManyToMany(mappedBy = "favouriteEateries")
    private Set<AppUser> favouriteUsers;

    @JdbcTypeCode(SqlTypes.JSON)
    private Set<BusinessDayTime> businessDayTimes = Set.of();

    public Eatery(String name, String address, Set<BusinessDayTime> businessDayTimes) {
        this.name = name;
        this.address = address;
        this.businessDayTimes = businessDayTimes;
    }

    public boolean isOpen() {
        return this.businessDayTimes.stream()
                .anyMatch(it ->
                        it.openDay().equals(LocalDateTime.now().getDayOfWeek()) &&
                        LocalTime.now().isAfter(it.openTime()) && LocalTime.now().isBefore(it.closeTime())
                );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Eatery eatery)) return false;
        return Objects.equals(id, eatery.id) && Objects.equals(name, eatery.name) &&
                Objects.equals(address, eatery.address) && Objects.equals(email, eatery.email) &&
                Objects.equals(phoneNumber, eatery.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, email, phoneNumber);
    }

    @Override
    public String toString() {
        return "Eatery{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", businessDayTimes=" + businessDayTimes +
                '}';
    }
}
