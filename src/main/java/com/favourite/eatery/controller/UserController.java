package com.favourite.eatery.controller;

import com.favourite.eatery.dto.UpdateUserRequest;
import com.favourite.eatery.exception.UserBadRequestException;
import com.favourite.eatery.model.Eatery;
import com.favourite.eatery.model.AppUser;
import com.favourite.eatery.service.AppUserService;
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
    private AppUserService userService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "500", description = "Users could not be fetched")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<AppUser> getAll() {
        return userService.findAll();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "User could not be created")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    AppUser create(@RequestBody AppUser newUser) {
        return userService.save(newUser);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "User could not be fetched")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUser get(@PathVariable Long id) {
        return userService.findById(id); // Hier wird die UserNotFoundException bereits in findById behandelt
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "User could not be updated")
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUser replace(@RequestBody UpdateUserRequest newUser, @PathVariable Long id) {
        AppUser user = userService.findById(id); // Hol den Benutzer, wirft eine Ausnahme, wenn nicht gefunden
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPhoneNumber(newUser.getPhoneNumber());
        return userService.save(user); // Speichere die Änderungen und gib den Benutzer zurück
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "User could not be deleted")
    })
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "User favourite could not be added")
    })
    @PutMapping(path = "/{userId}/favourites", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUser addUserFavourite(@RequestBody Eatery newEatery, @PathVariable Long userId) {
        AppUser user = userService.findById(userId); // Benutzer abrufen, Ausnahme wird geworfen, wenn nicht gefunden
        user.getFavouriteEateries().add(newEatery); // Neue Lieblings-Eatery hinzufügen
        return userService.save(user); // Benutzer mit aktualisierten Favoriten speichern
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "User favourite could not be deleted")
    })
    @DeleteMapping(path = "/{userId}/favourites/{eateryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUser deleteUserFavourite(@PathVariable Long userId, @PathVariable Long eateryId) {
        AppUser user = userService.findById(userId); // Benutzer abrufen, Ausnahme wird geworfen, wenn nicht gefunden

        // Entfernen der Lieblings-Eatery, wenn vorhanden
        boolean isRemoved = user.getFavouriteEateries().removeIf(
                favourite -> favourite.getId().equals(eateryId)
        );

        // Wenn die Eatery entfernt wurde, speichern wir den Benutzer
        if (isRemoved) {
            return userService.save(user); // Änderungen speichern
        } else {
            throw new UserBadRequestException("Eatery not found in favorites."); // Ausnahme werfen, wenn die Eatery nicht gefunden wird
        }
    }
}
