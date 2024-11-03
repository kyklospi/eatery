package com.favourite.eatery.service;

import com.favourite.eatery.dto.UpdateUserRequest;
import com.favourite.eatery.exception.AdminNotFoundException;
import com.favourite.eatery.model.Administrator;
import com.favourite.eatery.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;

    /**
     * Get all administrators
     * @return all administrators
     */
    public List<Administrator> getAll() {
        return adminRepository.findAll();
    }

    public Administrator create(UpdateUserRequest newAdmin) {
        return adminRepository.save(
                new Administrator(
                        newAdmin.getFirstName(),
                        newAdmin.getLastName(),
                        newAdmin.getEmail(),
                        newAdmin.getPhoneNumber()
                ));
    }

    public Administrator get(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException(id));
    }

    public Administrator replace(UpdateUserRequest newAdmin, Long id) {
        return adminRepository.findById(id)
                .map(admin -> {
                    admin.setFirstName(newAdmin.getFirstName());
                    admin.setLastName(newAdmin.getLastName());
                    admin.setEmail(newAdmin.getEmail());
                    admin.setPhoneNumber(newAdmin.getPhoneNumber());
                    return adminRepository.save(admin);
                })
                .orElseThrow(() -> new AdminNotFoundException(id));
    }

    public void delete(Long id) {
        adminRepository.deleteById(id);
    }
}
