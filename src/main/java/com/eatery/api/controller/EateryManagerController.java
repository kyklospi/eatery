package com.eatery.api.controller;

import com.eatery.api.dto.UpdateUserRequest;
import com.eatery.entity.EateryManager;
import com.eatery.api.service.EateryManagerService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/managers")
public class EateryManagerController {
    @Autowired
    private EateryManagerService eateryManagerService;

    /**
     * Fetches all eatery managers from the database.
     * @return A list of all eatery managers.
     */
    @ApiResponses(value = {
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
            @ApiResponse(responseCode = "500", description = "Eatery manager could not be created")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EateryManager create(@RequestBody UpdateUserRequest newManager) {
        return eateryManagerService.create(newManager);
    }

    /**
     * Fetches an eatery manager by their ID.
     * @param id The ID of the eatery manager to retrieve.
     * @return The eatery manager with the specified ID.
     */
    @ApiResponses(value = {
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
            @ApiResponse(responseCode = "404", description = "Eatery manager not found"),
            @ApiResponse(responseCode = "500", description = "Eatery manager could not be updated")
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EateryManager replace(@RequestBody UpdateUserRequest newManager, @PathVariable Long id) {
        return eateryManagerService.replace(newManager, id);
    }

    /**
     * Deletes the eatery manager with the specified ID.
     * @param id The ID of the eatery manager to delete.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Eatery manager could not be deleted")
    })
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        eateryManagerService.delete(id);
    }
}
