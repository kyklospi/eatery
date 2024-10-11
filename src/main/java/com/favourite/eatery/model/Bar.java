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
public class Bar extends Eatery {

    public Bar(String name, String address, Set<BusinessDayTime> businessDayTimes, int guestCapacity, String email, String phoneNumber) {
        super(name, address, businessDayTimes, guestCapacity, email, phoneNumber);
    }

    @Override
    public String toString() {
        return "Bar{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", address='" + this.getAddress() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", phoneNumber='" + this.getPhoneNumber() + '\'' +
                ", guestCapacity=" + this.getGuestCapacity() +
                ", favouriteUsers=" + this.getFavouriteUsers() +
                ", businessDayTimes=" + this.getBusinessDayTimes() +
                '}';
    }
}
