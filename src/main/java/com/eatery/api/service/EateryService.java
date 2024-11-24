package com.eatery.api.service;

import com.eatery.api.dto.UpdateEateryRequest;
import com.eatery.exception.EateryBadRequestException;
import com.eatery.exception.EateryNotFoundException;
import com.eatery.entity.Eatery;
import com.eatery.repository.EateryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

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
                .orElseThrow(EateryNotFoundException::new);
    }

    public List<Eatery> search(String name, String address, String type) {
        Eatery.Type eateryType = null;
        ExampleMatcher caseInsensitiveMatcher = ExampleMatcher.matchingAny()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("address", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        if (type != null) {
            try {
                eateryType = Eatery.Type.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new EateryNotFoundException("type " + type);
            }
        }

        Example<Eatery> searchSample = Example.of(Eatery.from(eateryType, name, address), caseInsensitiveMatcher);
        return eateryRepository.findAll(searchSample);


    }

    public Eatery create(UpdateEateryRequest newEatery) {
        validateEatery(newEatery);
        Eatery eatery = new Eatery(
                Eatery.Type.valueOf(newEatery.getType()),
                newEatery.getName(),
                newEatery.getAddress(),
                newEatery.getBusinessDayTimes(),
                newEatery.getGuestCapacity(),
                newEatery.getEmail(),
                newEatery.getPhoneNumber()
        );
        return eateryRepository.save(eatery);
    }

    public Eatery replace(UpdateEateryRequest newEatery, Long id) {
        validateEatery(newEatery);
        return eateryRepository.findById(id)
                .map(eatery -> {
                    eatery.setType(Eatery.Type.valueOf(newEatery.getType()));
                    eatery.setName(newEatery.getName());
                    eatery.setAddress(newEatery.getAddress());
                    eatery.setEmail(newEatery.getEmail());
                    eatery.setPhoneNumber(newEatery.getPhoneNumber());
                    eatery.setGuestCapacity(newEatery.getGuestCapacity());
                    eatery.setBusinessDayTimes(newEatery.getBusinessDayTimes());
                    return eateryRepository.save(eatery);
                })
                .orElseThrow(EateryNotFoundException::new);
    }

    public void delete(Long id) {
        Eatery eatery = eateryRepository.findById(id)
                .orElseThrow(EateryNotFoundException::new);
        eateryRepository.delete(eatery);
    }

    private void validateEatery(UpdateEateryRequest eatery) {
        if (eatery == null) {
            throw new EateryBadRequestException("Eatery request must not be null.");
        }

        if (eatery.getType() == null || eatery.getType().isBlank()) {
            throw new EateryBadRequestException("Type must not be null or empty.");
        }
        try {
            Eatery.Type.valueOf(eatery.getType());
        } catch (Exception e) {
            throw new EateryBadRequestException("Type is invalid.");
        }

        if (eatery.getGuestCapacity() <= 0) {
            throw new EateryBadRequestException("Guest capacity must be greater than 0");
        }
        if (eatery.getName() == null || eatery.getName().isBlank()) {
            throw new EateryBadRequestException("Name must not be null or empty.");
        }
        if (eatery.getAddress() == null || eatery.getAddress().isBlank()) {
            throw new EateryBadRequestException("Address must not be null or empty.");
        }
        if (eatery.getEmail() == null || eatery.getEmail().isBlank()) {
            throw new EateryBadRequestException("Email must not be null or empty.");
        }
        if (eatery.getPhoneNumber() == null || eatery.getPhoneNumber().isBlank()) {
            throw new EateryBadRequestException("Phone Number must not be null or empty.");
        }
        if (eatery.getBusinessDayTimes() == null || eatery.getBusinessDayTimes().isEmpty()) {
            throw new EateryBadRequestException("Business day times must not be null or empty.");
        }
    }
}