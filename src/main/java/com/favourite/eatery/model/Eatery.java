package com.favourite.eatery.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Eatery {
    private @Id @GeneratedValue Long id;
    private Type type;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private int guestCapacity;

    @ManyToMany(mappedBy = "favouriteEateries")
    private Set<AppUser> favouriteUsers;

    @OneToMany(mappedBy = "eatery")
    private List<Reservation> reservationList;

    @JdbcTypeCode(SqlTypes.JSON)
    private Set<BusinessDayTime> businessDayTimes = Set.of();

    public Eatery(Type type, String name, String address, Set<BusinessDayTime> businessDayTimes, int guestCapacity, String email, String phoneNumber) {
        this.type = type;
        this.name = name;
        this.address = address;
        this.businessDayTimes = businessDayTimes;
        this.guestCapacity = guestCapacity;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public boolean isOpen() {
        return this.businessDayTimes.stream()
                .anyMatch(it ->
                        it.openDay().equals(LocalDateTime.now().getDayOfWeek()) &&
                        LocalTime.now().isAfter(it.openTime()) && LocalTime.now().isBefore(it.closeTime())
                );
    }

    /**
     * Checks if eatery guest capacity is reached from reservation time until 2 hours after
     * @param atTime new entry of reservation time
     * @return true if eatery guest capacity is reached
     */
    public boolean isFullyBooked(LocalDateTime atTime, int newGuestNumber) {
        List<Reservation> currentReservations = this.reservationList.stream()
                .filter(eateryReservation -> eateryReservation.getReservationDateTime().isAfter(atTime) &&
                        eateryReservation.getReservationDateTime().isBefore(atTime.plusHours(2)) &&
                        eateryReservation.getStatus().equals(Reservation.Status.CONFIRMED)
                )
                .toList();

        int currentGuestNumber = 0;
        for (Reservation reservation : currentReservations) {
            currentGuestNumber += reservation.getPersonNumber();
        }
        return (currentGuestNumber + newGuestNumber) > this.guestCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Eatery eatery)) return false;
        return guestCapacity == eatery.guestCapacity && Objects.equals(id, eatery.id) &&
                Objects.equals(type, eatery.type) && Objects.equals(name, eatery.name) &&
                Objects.equals(address, eatery.address) && Objects.equals(email, eatery.email) &&
                Objects.equals(phoneNumber, eatery.phoneNumber) && Objects.equals(businessDayTimes, eatery.businessDayTimes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, name, address, email, phoneNumber, guestCapacity, businessDayTimes);
    }

    @Override
    public String toString() {
        return "Eatery{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", guestCapacity=" + guestCapacity +
                ", favouriteUsers=" + favouriteUsers +
                ", reservationList=" + reservationList +
                ", businessDayTimes=" + businessDayTimes +
                '}';
    }

    public enum Type {
        RESTAURANT,
        CAFE,
        BAR
    }
}
