package com.favourite.eatery.controller;

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
        return userService.getAll();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "User could not be created")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    AppUser create(@RequestBody AppUser newUser) {
        return userService.create(newUser);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "User could not be fetched")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    AppUser get(@PathVariable Long id) {
        return userService.findById(id);
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "User not found"), @ApiResponse(responseCode = "500", description = "User could not be updated"), @ApiResponse(responseCode = "500", description = "User could not be deleted")})

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }

}
