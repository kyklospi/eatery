package com.favourite.eatery.controller;

import com.favourite.eatery.dto.UpdateUserRequest;
import com.favourite.eatery.exception.UserNotFoundException;
import com.favourite.eatery.model.Eatery;
import com.favourite.eatery.model.AppUser;
import com.favourite.eatery.repository.AppUserRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private AppUserRepository repository;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "500", description = "Users could not be fetched")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<AppUser> getAll() {
        return repository.findAll();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "User could not be created")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    AppUser create(@RequestBody AppUser newUser) {
        return repository.save(newUser);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "User could not be fetched")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    AppUser get(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "User could not be updated")
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    AppUser replace(@RequestBody UpdateUserRequest newUser, @PathVariable Long id) {
        return repository.findById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setEmail(newUser.getEmail());
                    user.setPhoneNumber(newUser.getPhoneNumber());
                    return repository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "User could not be deleted")
    })
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "User favourite could not be added")
    })
    @PutMapping(path = "/{userId}/favourites", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    AppUser addUserFavourite(@RequestBody Eatery newEatery, @PathVariable Long userId) {
        return repository.findById(userId)
                .map(user -> {
                    user.getFavouriteEateries().add(newEatery);
                    return repository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "User favourite could not be deleted")
    })
    @DeleteMapping(path = "/{userId}/favourites/{eateryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    AppUser deleteUserFavourite(@PathVariable Long userId, @PathVariable Long eateryId) {
        AppUser user = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.getFavouriteEateries().removeIf(
                favourite -> favourite.getId().equals(eateryId)
        );
        return user;
    }
}
