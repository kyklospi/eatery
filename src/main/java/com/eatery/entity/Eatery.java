package com.eatery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Eatery implements Reservable{
    private @Id @GeneratedValue Long id;
    private Type type;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    private String email;
    private String phoneNumber;
    private int guestCapacity;

    @ManyToMany(mappedBy = "favouriteEateries")
    private Set<Customer> favouriteCustomers;

    @OneToMany(mappedBy = "eatery")
    private List<Reservation> reservationList;

    @JdbcTypeCode(SqlTypes.JSON)
    private Set<BusinessDayTime> businessDayTimes = Set.of();

    @OneToOne(mappedBy = "eatery")
    private EateryManager eateryManager;

    public Eatery(Type type, String name, String address, Set<BusinessDayTime> businessDayTimes, int guestCapacity, String email, String phoneNumber) {
        this.type = type;
        this.name = name;
        this.address = address;
        this.businessDayTimes = businessDayTimes;
        this.guestCapacity = guestCapacity;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    private Eatery(Type type, String name, String address) {
        this.type = type;
        this.name = name;
        this.address = address;
    }

    /**
     * Static factory pattern
     * Creates Eatery object while isolating constructor
     * @param type Eatery type
     * @param name Eatery name
     * @param address Eatery address
     * @return Eatery object
      */
    public static Eatery from(Type type, String name, String address) {
        return new Eatery(type, name, address);
    }

    public boolean isOpen() {
        return this.businessDayTimes.stream()
                .anyMatch(it ->
                        it.openDay().equals(LocalDateTime.now().getDayOfWeek()) &&
                        LocalTime.now().isAfter(it.openTime()) && LocalTime.now().isBefore(it.closeTime())
                );
    }

    /**
     * Strategy pattern implementation
     * Checks if eatery is open at reservation time
     * @param reservationTime reservation time
     * @return true when eatery is open at reservation time
     */
    @Override
    public boolean isOpenAt(LocalDateTime reservationTime) {
        return this.businessDayTimes.stream()
                .anyMatch(it ->
                        it.openDay().equals(reservationTime.getDayOfWeek()) &&
                                reservationTime.isAfter(ChronoLocalDateTime.from(it.openTime())) &&
                                reservationTime.isBefore(ChronoLocalDateTime.from(it.closeTime()))
                );
    }

    /**
     * Strategy pattern implementation
     * Checks if eatery guest capacity is reached from reservation time until 2 hours after
     * @param atTime new entry of reservation time
     * @return true when eatery guest capacity is reached
     */
    @Override
    public boolean isFullyBooked(LocalDateTime atTime, int newGuestNumber) {
        List<Reservation> currentReservations = this.reservationList.stream()
                .filter(eateryReservation -> eateryReservation.getReservationDateTime().isAfter(atTime) &&
                        eateryReservation.getReservationDateTime().isBefore(atTime.plusHours(2)) &&
                        eateryReservation.getStatus().equals(Reservation.Status.CONFIRMED)
                )
                .toList();

        int currentGuestNumber = 0;
        for (Reservation reservation : currentReservations) {
            currentGuestNumber += reservation.getGuestNumber();
        }
        return (currentGuestNumber + newGuestNumber) > this.guestCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Eatery eatery)) return false;
        return guestCapacity == eatery.guestCapacity && Objects.equals(id, eatery.id) && type == eatery.type &&
                Objects.equals(name, eatery.name) && Objects.equals(address, eatery.address) &&
                Objects.equals(email, eatery.email) && Objects.equals(phoneNumber, eatery.phoneNumber) &&
                Objects.equals(eateryManager, eatery.eateryManager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, name, address, email, phoneNumber, guestCapacity, eateryManager);
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
                ", favouriteCustomers=" + favouriteCustomers +
                ", reservationList=" + reservationList +
                ", businessDayTimes=" + businessDayTimes +
                ", eateryManager=" + eateryManager +
                '}';
    }

    public enum Type {
        RESTAURANT,
        CAFE,
        BAR,
    }
}
