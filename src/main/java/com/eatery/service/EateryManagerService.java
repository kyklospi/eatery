package com.eatery.service;

import com.eatery.api.dto.UpdateManagerRequest;
import com.eatery.exception.CustomerNotFoundException;
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
    public EateryManager create(UpdateManagerRequest newManager) {
        EateryManager manager = new EateryManager(
                newManager.getFirstName(),
                newManager.getLastName(),
                newManager.getEmail(),
                newManager.getPhoneNumber(),
                newManager.getUsername(),
                newManager.getPassword(),
                newManager.getEateryId(),
                newManager.getJobTitle(),
                newManager.getWorkSchedules()
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
     * Retrieves an eatery manager by their username and password.
     * @param username user name
     * @param password password
     * @return The eatery manager object with the specified username and password.
     * @throws CustomerNotFoundException if the eatery manager with the specified username and password does not exist.
     */
    public EateryManager get(String username, String password) {
        return eateryManagerRepository.findAll().stream()
                .filter(eateryManager ->
                        eateryManager.getUsername().equals(username) && eateryManager.getPassword().equals(password)
                )
                .findFirst()
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
    public EateryManager replace(UpdateManagerRequest newManager, Long id) {
        return eateryManagerRepository.findById(id)
                .map(eateryManager -> {
                    eateryManager.setFirstName(newManager.getFirstName());
                    eateryManager.setLastName(newManager.getLastName());
                    eateryManager.setEmail(newManager.getEmail());
                    eateryManager.setPhoneNumber(newManager.getPhoneNumber());
                    eateryManager.setUsername(newManager.getUsername());
                    eateryManager.setPassword(newManager.getPassword());
                    eateryManager.setJobTitle(newManager.getJobTitle());
                    eateryManager.setWorkSchedules(newManager.getWorkSchedules());
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
        if (manager.getEmail() == null || manager.getEmail().isBlank()) {
            throw new EateryManagerBadRequestException("Email cannot be null or empty.");
        }
        if (manager.getFirstName() == null || manager.getFirstName().isBlank()) {
            throw new EateryManagerBadRequestException("First Name cannot be null or empty.");
        }
        if (manager.getLastName() == null || manager.getLastName().isBlank()) {
            throw new EateryManagerBadRequestException("Last Name cannot be null or empty.");
        }
        if (manager.getPhoneNumber() == null || manager.getPhoneNumber().isBlank()) {
            throw new EateryManagerBadRequestException("Phone Number cannot be null or empty.");
        }
        if (manager.getUsername() == null || manager.getUsername().isBlank()) {
            throw new EateryManagerBadRequestException("Username cannot be null or empty.");
        }
        if (manager.getPassword() == null || manager.getPassword().isBlank()) {
            throw new EateryManagerBadRequestException("Password cannot be null or empty.");
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
