package com.eatery.api.service;

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
                .orElseThrow(() -> new EateryNotFoundException(id));
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

        Example<Eatery> searchSample = Example.of(new Eatery(eateryType, name, address), caseInsensitiveMatcher);
        return eateryRepository.findAll(searchSample);
    }

    public Eatery save(Eatery newEatery) {
        validateEatery(newEatery);
        return eateryRepository.save(newEatery);
    }

    public Eatery replace(Eatery newEatery, Long id) {
        return eateryRepository.findById(id)
                .map(eatery -> {
                    eatery.setName(newEatery.getName());
                    eatery.setAddress(newEatery.getAddress());
                    eatery.setEmail(newEatery.getEmail());
                    eatery.setPhoneNumber(newEatery.getPhoneNumber());
                    return eateryRepository.save(eatery);
                })
                .orElseGet(() -> eateryRepository.save(newEatery));
    }

    public void deleteById(Long id) {
        eateryRepository.findById(id)
                .orElseThrow(() -> new EateryNotFoundException(id));

        eateryRepository.deleteById(id);
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