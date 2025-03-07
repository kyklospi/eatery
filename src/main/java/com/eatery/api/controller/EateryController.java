package com.eatery.api.controller;

import com.eatery.api.dto.UpdateEateryRequest;
import com.eatery.entity.Eatery;
import com.eatery.exception.EateryBadRequestException;
import com.eatery.exception.EateryNotFoundException;
import com.eatery.service.EateryService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Eatery Controller
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/eateries")
public class EateryController {
    @Autowired
    private EateryService eateryService;

    /**
     * Fetches all eateries from the database.
     * @return A list of all eateries.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned"),
            @ApiResponse(responseCode = "404", description = "Eateries not found"),
            @ApiResponse(responseCode = "500", description = "Eateries could not be fetched")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Eatery> getAll() {
        return eateryService.findAll();
    }

    /**
     * Searches for eateries based on the provided optional parameters.
     * @param name The name of the eatery to search for (optional).
     * @param address The address of the eatery to search for (optional).
     * @param type The type of the eatery to search for (optional).
     * @return A list of eateries that match the search criteria.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned"),
            @ApiResponse(responseCode = "404", description = "Eateries not found"),
            @ApiResponse(responseCode = "500", description = "Eateries could not be fetched")
    })
    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Eatery> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String type
    ) {
        return eateryService.search(name, address, type);
    }

    /**
     * Creates a new eatery.
     * @param newEatery The data for the new eatery to be created.
     * @return The created eatery.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Eatery could not be created")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Eatery create(@RequestBody UpdateEateryRequest newEatery) {
        return eateryService.create(newEatery);
    }

    /**
     * Fetches an eatery by its ID.
     * @param id The ID of the eatery to retrieve.
     * @return The eatery with the specified ID.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned"),
            @ApiResponse(responseCode = "404", description = "Eatery not found"),
            @ApiResponse(responseCode = "500", description = "Eatery could not be fetched")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Eatery get(@PathVariable Long id) {
        return eateryService.findById(id);
    }

    /**
     * Updates an existing eatery with the provided new details.
     * @param newEatery The new details for the eatery.
     * @param id The ID of the eatery to update.
     * @return The updated eatery.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully modified"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Eatery not found"),
            @ApiResponse(responseCode = "500", description = "Eatery could not be updated")
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Eatery replace(@RequestBody UpdateEateryRequest newEatery, @PathVariable Long id) {
        return eateryService.replace(newEatery, id);
    }

    /**
     * Deletes the eatery with the specified ID.
     * @param id The ID of the eatery to delete.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Eatery not found"),
            @ApiResponse(responseCode = "500", description = "Eatery could not be updated"), @ApiResponse(responseCode = "500", description = "Eatery could not be deleted")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        eateryService.delete(id);
    }


    /**
     * Exception handlers for handling Eatery-related exceptions.
     * Below methods handle exceptions related to Eatery and sends the appropriate HTTP responses.
     */
    @ExceptionHandler(EateryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handle(EateryNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(EateryBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handle(EateryBadRequestException e) {
        return e.getMessage();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String handle(RuntimeException e) {
        return e.getMessage();
    }
}
