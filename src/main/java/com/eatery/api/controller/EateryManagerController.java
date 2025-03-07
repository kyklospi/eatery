package com.eatery.api.controller;

import com.eatery.api.dto.UpdateManagerRequest;
import com.eatery.entity.EateryManager;
import com.eatery.exception.EateryManagerBadRequestException;
import com.eatery.exception.EateryManagerNotFoundException;
import com.eatery.service.EateryManagerService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/managers")
public class EateryManagerController {
    @Autowired
    private EateryManagerService eateryManagerService;

    /**
     * Fetches all eatery managers from the database.
     * @return A list of all eatery managers.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned"),
            @ApiResponse(responseCode = "404", description = "Eatery managers not found"),
            @ApiResponse(responseCode = "500", description = "Eatery managers could not be fetched")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<EateryManager> getAll() {
        return eateryManagerService.getAll();
    }

    /**
     * Creates a new eatery manager.
     * @param newManager The data for the new eatery manager to be created.
     * @return The created eatery manager.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Eatery manager could not be created")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EateryManager create(@RequestBody UpdateManagerRequest newManager) {
        return eateryManagerService.create(newManager);
    }

    /**
     * Fetches an eatery manager by their ID.
     * @param id The ID of the eatery manager to retrieve.
     * @return The eatery manager with the specified ID.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned"),
            @ApiResponse(responseCode = "404", description = "Eatery manager not found"),
            @ApiResponse(responseCode = "500", description = "Eatery manager could not be fetched")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    EateryManager get(@PathVariable Long id) {
        return eateryManagerService.get(id);
    }

    /**
     * Updates an existing eatery manager with the provided new details.
     * @param newManager The new details for the eatery manager.
     * @param id The ID of the eatery manager to update.
     * @return The updated eatery manager.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully modified"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Eatery manager not found"),
            @ApiResponse(responseCode = "500", description = "Eatery manager could not be updated")
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EateryManager replace(@RequestBody UpdateManagerRequest newManager, @PathVariable Long id) {
        return eateryManagerService.replace(newManager, id);
    }

    /**
     * Deletes the eatery manager with the specified ID.
     * @param id The ID of the eatery manager to delete.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Eatery manager not found"),
            @ApiResponse(responseCode = "500", description = "Eatery manager could not be deleted")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        eateryManagerService.delete(id);
    }

    /**
     * Fetches an eatery manager by their username and password.
     * @param username The username of the eatery manager to retrieve.
     * @param password The password of the eatery manager to retrieve.
     * @return The eatery manager with the specified username and password.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned"),
            @ApiResponse(responseCode = "404", description = "Eatery manager not found"),
            @ApiResponse(responseCode = "500", description = "Eatery manager could not be fetched")
    })
    @GetMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    EateryManager login(String username, String password) {
        return eateryManagerService.get(username, password);
    }

    /**
     * Exception handlers for EateryManager-related exceptions.
     * Below methods handle exceptions related to EateryManager and sends the appropriate HTTP responses.
     */
    @ExceptionHandler(EateryManagerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handle(EateryManagerNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(EateryManagerBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handle(EateryManagerBadRequestException e) {
        return e.getMessage();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String handle(RuntimeException e) {
        return e.getMessage();
    }
}
