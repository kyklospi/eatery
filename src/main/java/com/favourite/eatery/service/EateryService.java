package com.favourite.eatery.service;

import com.favourite.eatery.exception.EateryNotFoundException;
import com.favourite.eatery.exception.EateryBadRequestException;
import com.favourite.eatery.model.Eatery;
import com.favourite.eatery.repository.EateryRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Eatery save(Eatery newEatery) {
        validateEatery(newEatery);
        return eateryRepository.save(newEatery);
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