package com.favourite.eatery.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Restaurant extends Eatery {
    public Restaurant(String name, String address, Set<BusinessDayTime> businessDayTimes, int guestCapacity, String email, String phoneNumber) {
        super(name, address, businessDayTimes, guestCapacity, email, phoneNumber);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", address='" + this.getAddress() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", phoneNumber='" + this.getPhoneNumber() + '\'' +
                ", guestCapacity=" + this.getGuestCapacity() +
                ", favouriteUsers=" + this.getFavouriteUsers() +
                ", reservationList=" + this.getReservationList() +
                ", businessDayTimes=" + this.getBusinessDayTimes() +
                '}';
    }
}
