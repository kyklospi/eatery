package com.favourite.eatery.controller;

import com.favourite.eatery.exception.UserNotFoundException;
import com.favourite.eatery.model.Eatery;
import com.favourite.eatery.model.AppUser;
import com.favourite.eatery.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private AppUserRepository repository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<AppUser> getAll() {
        return repository.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    AppUser create(@RequestBody AppUser newUser) {
        return repository.save(newUser);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    AppUser get(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping(path = "/{userId}/favourites", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Eatery> getUserFavourites(@PathVariable Long userId) {
        AppUser user = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return user.getFavouriteEateries();
    }

    @PutMapping(path = "/{userId}/favourites", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    AppUser addUserFavourite(@RequestBody Eatery newEatery, @PathVariable Long userId) {
        return repository.findById(userId)
                .map(user -> {
                    user.getFavouriteEateries().add(newEatery);
                    return repository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @DeleteMapping(path = "/{userId}/favourites/{eateryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Eatery> deleteUserFavourite(@PathVariable Long userId, @PathVariable Long eateryId) {
        AppUser user = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.getFavouriteEateries().removeIf(
                favourite -> favourite.getId().equals(eateryId)
        );
        return user.getFavouriteEateries();
    }
}
