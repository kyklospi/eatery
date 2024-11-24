package com.eatery.api.service;

import com.eatery.api.dto.UpdateUserRequest;
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

    public EateryManager create(UpdateUserRequest newManager) {
        return eateryManagerRepository.save(
                new EateryManager(
                        newManager.getFirstName(),
                        newManager.getLastName(),
                        newManager.getEmail(),
                        newManager.getPhoneNumber()
                ));
    }

    public EateryManager get(Long id) {
        return eateryManagerRepository.findById(id)
                .orElseThrow(() -> new EateryManagerNotFoundException(id));
    }

    public EateryManager replace(UpdateUserRequest newManager, Long id) {
        return eateryManagerRepository.findById(id)
                .map(eateryManager -> {
                    eateryManager.setFirstName(newManager.getFirstName());
                    eateryManager.setLastName(newManager.getLastName());
                    eateryManager.setEmail(newManager.getEmail());
                    eateryManager.setPhoneNumber(newManager.getPhoneNumber());
                    return eateryManagerRepository.save(eateryManager);
                })
                .orElseThrow(() -> new EateryManagerNotFoundException(id));
    }

    public void delete(Long id) {
        EateryManager eateryManager = eateryManagerRepository.findById(id)
                        .orElseThrow(() -> new EateryManagerNotFoundException(id));
        eateryManagerRepository.delete(eateryManager);
    }
}
