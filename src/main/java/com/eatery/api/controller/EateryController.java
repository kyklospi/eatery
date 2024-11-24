package com.eatery.api.controller;

import com.eatery.api.dto.UpdateEateryRequest;
import com.eatery.entity.Eatery;
import com.eatery.api.service.EateryService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Eatery Controller
 */
@RestController
@RequestMapping(path = "/eateries")
public class EateryController {
    @Autowired
    private EateryService eateryService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Eateries not found"),
            @ApiResponse(responseCode = "500", description = "Eateries could not be fetched")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Eatery> getAll() {
        return eateryService.findAll();
    }

    @ApiResponses(value = {
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Eatery could not be created")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Eatery create(@RequestBody UpdateEateryRequest newEatery) {
        return eateryService.create(newEatery);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Eatery not found"),
            @ApiResponse(responseCode = "500", description = "Eatery could not be fetched")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Eatery get(@PathVariable Long id) {
        return eateryService.findById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Eatery not found"),
            @ApiResponse(responseCode = "500", description = "Eatery could not be updated")
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Eatery replace(@RequestBody UpdateEateryRequest newEatery, @PathVariable Long id) {
        return eateryService.replace(newEatery, id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Eatery not found"),
            @ApiResponse(responseCode = "500", description = "Eatery could not be updated"), @ApiResponse(responseCode = "500", description = "Eatery could not be deleted")
    })
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        eateryService.delete(id);
    }
}
