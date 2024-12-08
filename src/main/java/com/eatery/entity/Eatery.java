package com.eatery.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Eatery implements Reservable {
    @Column(unique = true, nullable = false)
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String email;
    private String phoneNumber;
    private int guestCapacity;

    @OneToMany(mappedBy = "eateryId")
    private List<Reservation> reservationList;

    @JdbcTypeCode(SqlTypes.JSON)
    private Set<BusinessDayTime> businessDayTimes = Set.of();

    private long managerId;

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
                                reservationTime.getHour() >= it.openTime().getHour() &&
                                reservationTime.getMinute() >= it.openTime().getMinute() &&
                                reservationTime.getHour() <= it.closeTime().getHour() &&
                                reservationTime.getMinute() <= it.closeTime().getMinute()
                );
    }

    /**
     * Strategy pattern implementation
     * Checks if eatery guest capacity is reached from 2 hours before reservation time until 2 hours after reservation time
     * @param atTime new entry of reservation time
     * @return true when eatery guest capacity is reached
     */
    @Override
    public boolean isFullyBooked(LocalDateTime atTime, int newGuestNumber) {
        List<Reservation> confirmedReservationsAtDuration = getConfirmedReservationsAtTimeSlot(atTime.minusHours(2), atTime.plusHours(2));
        int totalGuestNumberAtDuration = countGuestNumber(confirmedReservationsAtDuration);

        return (totalGuestNumberAtDuration + newGuestNumber) > this.guestCapacity;
    }

    private List<Reservation> getConfirmedReservationsAtTimeSlot(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return this.reservationList.stream()
                .filter(eateryReservation -> eateryReservation.getReservationDateTime().isAfter(startDateTime) &&
                        eateryReservation.getReservationDateTime().isBefore(endDateTime) &&
                        eateryReservation.getStatus().equals(Reservation.Status.CONFIRMED)
                )
                .toList();

    }

    private int countGuestNumber(List<Reservation> reservations) {
        int sum = 0;
        for (Reservation reservation : reservations) {
            sum += reservation.getGuestNumber();
        }
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Eatery eatery)) return false;
        return guestCapacity == eatery.guestCapacity && Objects.equals(id, eatery.id) && type == eatery.type &&
                Objects.equals(name, eatery.name) && Objects.equals(address, eatery.address) &&
                Objects.equals(email, eatery.email) && Objects.equals(phoneNumber, eatery.phoneNumber) &&
                Objects.equals(managerId, eatery.managerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, name, address, email, phoneNumber, guestCapacity, managerId);
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
                ", reservationList=" + reservationList +
                ", businessDayTimes=" + businessDayTimes +
                ", eateryManagerId=" + managerId +
                '}';
    }

    public enum Type {
        RESTAURANT,
        CAFE,
        BAR,
    }
}
