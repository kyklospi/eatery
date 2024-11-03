package com.favourite.eatery.service;

import com.favourite.eatery.exception.EateryNotFoundException;
import com.favourite.eatery.exception.EateryBadRequestException;
import com.favourite.eatery.model.Eatery;
import com.favourite.eatery.repository.EateryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class EateryService {

    @Autowired
    private EateryRepository eateryRepository;

    public List<Eatery> findAll() {
        return eateryRepository.findAll();
    }

    public Eatery findById(Long id) {
        return eateryRepository.findById(id)
                .orElseThrow(() -> new EateryNotFoundException(id));
    }

    public Eatery save(Eatery newEatery) {
        validateEatery(newEatery);
        return eateryRepository.save(newEatery);
    }

    public Eatery update(Long id, Eatery updatedEatery) {
        return eateryRepository.findById(id)
                .map(eatery -> {
                    eatery.setName(updatedEatery.getName());
                    eatery.setAddress(updatedEatery.getAddress());
                    eatery.setEmail(updatedEatery.getEmail());
                    eatery.setPhoneNumber(updatedEatery.getPhoneNumber());
                    eatery.setType(updatedEatery.getType());
                    eatery.setGuestCapacity(updatedEatery.getGuestCapacity());
                    eatery.setBusinessDayTimes(updatedEatery.getBusinessDayTimes());
                    return eateryRepository.save(eatery);
                })
                .orElseThrow(() -> new EateryNotFoundException(id));
    }

    public void deleteById(Long id) {
        eateryRepository.findById(id)
                .orElseThrow(() -> new EateryNotFoundException(id));

        eateryRepository.deleteById(id);
    }

    public boolean isOpen(Long id, DayOfWeek day, LocalTime time) {
        Eatery eatery = findById(id);

        return eatery.getBusinessDayTimes().stream()
                .anyMatch(businessDayTime ->
                        businessDayTime.openDay() == day &&
                                time.isAfter(businessDayTime.openTime()) &&
                                time.isBefore(businessDayTime.closeTime()));
    }

    public boolean isFullyBooked(Long id, LocalDateTime dateTime, int personNumber) {
        Eatery eatery = findById(id);

        int reservedCapacity = eatery.getReservationList().stream()
                .filter(reservation -> reservation.getReservationDateTime().toLocalDate().equals(dateTime.toLocalDate()) &&
                        reservation.getReservationDateTime().toLocalTime().equals(dateTime.toLocalTime()))
                .mapToInt(reservation -> reservation.getPersonNumber())
                .sum();

        return (reservedCapacity + personNumber) > eatery.getGuestCapacity();
    }

    private void validateEatery(Eatery eatery) {
        if (eatery.getGuestCapacity() <= 0) {
            throw new EateryBadRequestException("Guest capacity must be greater than 0");
        }
        if (eatery.getName() == null || eatery.getName().isEmpty()) {
            throw new EateryBadRequestException("Name must not be empty");
        }
        if (eatery.getAddress() == null || eatery.getAddress().isEmpty()) {
            throw new EateryBadRequestException("Address must not be empty");
        }
    }
}
