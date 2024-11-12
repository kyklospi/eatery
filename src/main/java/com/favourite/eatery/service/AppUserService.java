package com.favourite.eatery.service;

import com.favourite.eatery.exception.UserBadRequestException;
import com.favourite.eatery.exception.UserNotFoundException;
import com.favourite.eatery.model.AppUser;
import com.favourite.eatery.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    @Autowired
    AppUserRepository userRepository;

    public List<AppUser> getAll() {
        return userRepository.findAll();
    }

    public AppUser  create(AppUser newUser) {
        validateUser(newUser);
        return userRepository.save(newUser);
    }

    public void deleteById(Long id) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
    }

    public AppUser findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void validateUser(AppUser user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new UserBadRequestException("Email cannot be null or empty.");
        }
        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new UserBadRequestException("First Name cannot be null or empty.");
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new UserBadRequestException("Last Name cannot be null or empty.");
        }
        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            throw new UserBadRequestException("Phone Number cannot be null or empty.");
        }

    }



}