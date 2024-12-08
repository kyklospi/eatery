package com.eatery.api.service;

import com.eatery.api.dto.UpdateUserRequest;
import com.eatery.exception.EateryManagerBadRequestException;
import com.eatery.exception.EateryManagerNotFoundException;
import com.eatery.entity.EateryManager;
import com.eatery.repository.EateryManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling change request of EateryManager object
 */
@Service
public class EateryManagerService {
    @Autowired
    private EateryManagerRepository eateryManagerRepository;

    /**
     * Get all eatery managers
     * @return all eatery managers
     */
    public List<EateryManager> getAll() {
        return eateryManagerRepository.findAll();
    }

    /**
     * Creates a new EateryManager from the provided update request.
     * Performs validation before saving the manager to the repository.
     * @param newManager The update request containing new manager information.
     * @return The created EateryManager object.
     * @throws EateryManagerBadRequestException if the provided manager data is invalid.
     */
    public EateryManager create(UpdateUserRequest newManager) {
        EateryManager manager = new EateryManager(
                newManager.getFirstName(),
                newManager.getLastName(),
                newManager.getEmail(),
                newManager.getPhoneNumber()
        );
        validateEateryManager(manager); // Validierung vor dem Speichern
        return eateryManagerRepository.save(manager);
    }


    /**
     * Retrieves an EateryManager by their ID.
     * @param id The ID of the EateryManager to retrieve.
     * @return The EateryManager object with the specified ID.
     * @throws EateryManagerNotFoundException if the manager with the specified ID does not exist.
     */
    public EateryManager get(Long id) {
        return eateryManagerRepository.findById(id)
                .orElseThrow(EateryManagerNotFoundException::new);
    }

    /**
     * Replaces an existing EateryManager's information with the provided update request.
     * Performs validation before saving the updated manager to the repository.
     * @param newManager The update request containing new manager information.
     * @param id The ID of the EateryManager to be updated.
     * @return The updated EateryManager object.
     * @throws EateryManagerNotFoundException if the manager with the specified ID does not exist.
     * @throws EateryManagerBadRequestException if the provided manager data is invalid.
     */
    public EateryManager replace(UpdateUserRequest newManager, Long id) {
        return eateryManagerRepository.findById(id)
                .map(eateryManager -> {
                    eateryManager.setFirstName(newManager.getFirstName());
                    eateryManager.setLastName(newManager.getLastName());
                    eateryManager.setEmail(newManager.getEmail());
                    eateryManager.setPhoneNumber(newManager.getPhoneNumber());
                    validateEateryManager(eateryManager); // Validierung vor dem Speichern
                    return eateryManagerRepository.save(eateryManager);
                })
                .orElseThrow(EateryManagerNotFoundException::new);
    }

    /**
     * Validates the provided EateryManager to ensure required fields are not null or empty.
     * @param manager The EateryManager object to be validated.
     * @throws EateryManagerBadRequestException if any required field is missing or empty.
     */
    private void validateEateryManager(EateryManager manager) {
        if (manager.getEmail() == null || manager.getEmail().isEmpty()) {
            throw new EateryManagerBadRequestException("Email cannot be null or empty.");
        }
        if (manager.getFirstName() == null || manager.getFirstName().isEmpty()) {
            throw new EateryManagerBadRequestException("First Name cannot be null or empty.");
        }
        if (manager.getLastName() == null || manager.getLastName().isEmpty()) {
            throw new EateryManagerBadRequestException("Last Name cannot be null or empty.");
        }
        if (manager.getPhoneNumber() == null || manager.getPhoneNumber().isEmpty()) {
            throw new EateryManagerBadRequestException("Phone Number cannot be null or empty.");
        }
    }

    /**
     * Deletes an EateryManager by their ID.
     * @param id The ID of the EateryManager to delete.
     */
    public void delete(Long id) {
        EateryManager eateryManager = eateryManagerRepository.findById(id)
                        .orElseThrow(EateryManagerNotFoundException::new);
        eateryManagerRepository.delete(eateryManager);
    }
}
