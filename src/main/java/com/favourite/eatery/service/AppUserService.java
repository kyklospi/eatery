package com.favourite.eatery.service;

import com.favourite.eatery.dto.UpdateUserRequest;
import com.favourite.eatery.exception.UserBadRequestException;
import com.favourite.eatery.exception.UserNotFoundException;
import com.favourite.eatery.model.AppUser;
import com.favourite.eatery.model.Eatery;
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

    public AppUser getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public AppUser save(AppUser newUser) {
        validateUser(newUser);
        return userRepository.save(newUser);
    }

    public AppUser updateUser(UpdateUserRequest updateUserRequest, Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(updateUserRequest.getFirstName());
                    user.setLastName(updateUserRequest.getLastName());
                    user.setEmail(updateUserRequest.getEmail());
                    user.setPhoneNumber(updateUserRequest.getPhoneNumber());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
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

    public AppUser addUserFavourite(Long userId, Eatery newEatery) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (user.getFavouriteEateries().contains(newEatery)) {
            throw new UserBadRequestException("Eatery is already a favorite.");
        }

        user.getFavouriteEateries().add(newEatery);
        return userRepository.save(user);
    }

    public AppUser deleteUserFavourite(Long userId, Long eateryId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (user.getFavouriteEateries().removeIf(eatery -> eatery.getId().equals(eateryId))) {
            return userRepository.save(user);
        } else {
            throw new UserBadRequestException("Eatery not found in favorites.");
        }
    }


    private void validateUser(AppUser user) {
        // Hier kannst du Validierungslogik für die Benutzer hinzufügen, z.B.:
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new UserBadRequestException("Email cannot be null or empty.");
        }
        // Weitere Validierungen...
    }

    public List<AppUser> findAll() {
        return userRepository.findAll();
    }


}

