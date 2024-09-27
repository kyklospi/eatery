package com.favourite.eatery.controller;

import com.favourite.eatery.exception.UserNotFoundException;
import com.favourite.eatery.model.Eatery;
import com.favourite.eatery.model.AppUser;
import com.favourite.eatery.repository.AppUserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final AppUserRepository repository;

    public UserController(AppUserRepository userRepository) {
        this.repository = userRepository;
    }

    @GetMapping("/users")
    List<AppUser> getAll() {
        return repository.findAll();
    }

    @PostMapping("/user")
    AppUser create(@RequestBody AppUser newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/user/{id}")
    AppUser get(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    AppUser replace(@RequestBody AppUser newUser, @PathVariable Long id) {
        return repository.findById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setFavouriteEateries(newUser.getFavouriteEateries());
                    return repository.save(user);
                })
                .orElseGet(() -> repository.save(newUser));
    }

    @DeleteMapping("/user/{id}")
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/user/{userId}/favourites")
    List<Eatery> getUserFavourites(@PathVariable Long userId) {
        AppUser user = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return user.getFavouriteEateries();
    }

    @PutMapping("/user/{userId}/favourite")
    AppUser addUserFavourite(@RequestBody Eatery newEatery, @PathVariable Long userId) {
        return repository.findById(userId)
                .map(user -> {
                    user.getFavouriteEateries().add(newEatery);
                    return repository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @DeleteMapping("/user/{userId}/favourite/{eateryId}")
    List<Eatery> deleteUserFavourite(@PathVariable Long userId, @PathVariable Long eateryId) {
        AppUser user = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.getFavouriteEateries().removeIf(
                favourite -> favourite.getId().equals(eateryId)
        );
        return user.getFavouriteEateries();
    }
}
