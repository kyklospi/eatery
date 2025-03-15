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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Eatery {
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

    // ensures the collection is fetched when the parent entity is loaded,
    // to access the collection without encountering LazyInitializationException.
    @OneToMany(mappedBy = "eateryId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Reservation> reservationList;

    @JdbcTypeCode(SqlTypes.JSON)
    private Set<BusinessDayTime> businessDayTimes = Set.of();

    /**
     * List of reviews associated with the eatery.
     * This is a one-to-many relationship where the `Review` entity contains a `eateryId` field
     * mapped to this class.
     */
    @OneToMany(mappedBy = "eateryId", cascade = CascadeType.ALL)
    private List<Review> reviews;

    private int rating;

    private long managerId;

    public Eatery(Type type, String name, String address, Set<BusinessDayTime> businessDayTimes, int guestCapacity, String email, String phoneNumber) {
        this.type = type;
        this.name = name;
        this.address = address;
        this.businessDayTimes = businessDayTimes;
        this.guestCapacity = guestCapacity;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.reviews = new ArrayList<>();
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
                        it.day().equals(LocalDateTime.now().getDayOfWeek()) &&
                        LocalTime.now().isAfter(it.startTime()) && LocalTime.now().isBefore(it.endTime())
                );
    }

    public int getRating() {
        if (this.reviews == null || this.reviews.isEmpty()) {
            return 0;
        }
        return this.reviews.stream().mapToInt(Review::getRating).sum() / reviews.size();
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
                ", reviews=" + reviews +
                ", eateryManagerId=" + managerId +
                ", manager=" + managerId +
                ", rating=" + rating +
                '}';
    }

    public enum Type {
        RESTAURANT,
        CAFE,
        BAR,
    }
}
