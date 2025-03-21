package com.eatery.service;

import com.eatery.api.dto.CreateEateryRequest;
import com.eatery.api.dto.UpdateEateryRequest;
import com.eatery.entity.EateryManager;
import com.eatery.exception.EateryBadRequestException;
import com.eatery.exception.EateryNotFoundException;
import com.eatery.entity.Eatery;
import com.eatery.repository.EateryManagerRepository;
import com.eatery.repository.EateryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for managing Eatery objects.
 * This class handles CRUD operations, search functionality, and validation for Eatery entities.
 */
@Service
public class EateryService {

    @Autowired
    private EateryRepository eateryRepository;
    @Autowired
    private EateryManagerRepository eateryManagerRepository;

    /**
     * Retrieves all eateries from the database.
     * @return A list of all eateries.
     */
    public List<Eatery> findAll() {
        return eateryRepository.findAll();
    }

    /**
     * Retrieves an Eatery by its ID.
     * @param id The ID of the Eatery to retrieve.
     * @return The Eatery object with the specified ID.
     * @throws EateryNotFoundException if the eatery with the specified ID does not exist.
     */
    public Eatery findById(Long id) {
        return eateryRepository.findById(id)
                .orElseThrow(EateryNotFoundException::new);
    }

    /**
     * Searches for eateries based on the provided parameters.
     * Performs a case-insensitive search for name, address, and type.
     * @param name The name of the eatery to search for (partial match allowed).
     * @param address The address of the eatery to search for (partial match allowed).
     * @param type The type of the eatery to search for (optional).
     * @return A list of eateries that match the search criteria.
     * @throws EateryNotFoundException if an invalid eatery type is provided.
     */
    public List<Eatery> search(String name, String address, String type) {
        return eateryRepository.findAll().stream()
                .filter(eatery -> (name == null || eatery.getName().equalsIgnoreCase(name)) &&
                        (address == null || eatery.getAddress().equalsIgnoreCase(address)) &&
                        (type == null || eatery.getType().equals(Eatery.Type.valueOf(type.toUpperCase()))))
                .toList();
    }

    /**
     * Saves a new Eatery after validating the provided data.
     * @param newEatery The Eatery object to be saved.
     * @return The saved Eatery object.
     * @throws EateryBadRequestException if the provided eatery data is invalid.
     */
    public Eatery create(CreateEateryRequest newEatery) {
        validateCreateEatery(newEatery);
        EateryManager manager = eateryManagerRepository.findById(newEatery.getManagerId())
                .orElseThrow(() -> new EateryBadRequestException("Invalid manager id"));

        Eatery eatery = new Eatery(
                Eatery.Type.valueOf(newEatery.getType()),
                newEatery.getName(),
                newEatery.getAddress(),
                newEatery.getBusinessDayTimes(),
                newEatery.getGuestCapacity(),
                newEatery.getEmail(),
                newEatery.getPhoneNumber()
        );

        eatery.setManagerId(newEatery.getManagerId());
        Eatery createdEatery = eateryRepository.save(eatery);
        manager.setEateryId(createdEatery.getId());

        return createdEatery;
    }

    /**
     * Replaces an existing Eatery with the provided data.
     * If the eatery does not exist, the new eatery will be saved as a new entry.
     * @param newEatery The new Eatery data.
     * @param id The ID of the Eatery to replace.
     * @return The updated Eatery object.
     * @throws EateryNotFoundException if the eatery with the specified ID does not exist.
     */
    public Eatery replace(UpdateEateryRequest newEatery, Long id) {
        validateUpdateEatery(newEatery);
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

    /**
     * Deletes an Eatery by its ID.
     * @param id The ID of the Eatery to delete.
     * @throws EateryNotFoundException if the eatery with the specified ID does not exist.
     */
    public void delete(Long id) {
        Eatery eatery = eateryRepository.findById(id)
                .orElseThrow(EateryNotFoundException::new);
        eateryRepository.delete(eatery);
    }

    /**
     * Validates the provided Eatery to ensure required fields are properly filled.
     * @param eatery The Eatery object to be validated.
     * @throws EateryBadRequestException if any required field is missing or invalid.
     */
    private void validateCreateEatery(CreateEateryRequest eatery) {
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
        if (eatery.getManagerId() == 0) {
            throw new EateryBadRequestException("Manager id must not be null or empty.");
        }
    }

    /**
     * Validates the provided Eatery to ensure required fields are properly filled.
     * @param eatery The Eatery object to be validated.
     * @throws EateryBadRequestException if any required field is missing or invalid.
     */
    private void validateUpdateEatery(UpdateEateryRequest eatery) {
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