package com.favourite.eatery.controller;

import com.favourite.eatery.model.Eatery;
import com.favourite.eatery.service.EateryService;
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
            @ApiResponse(responseCode = "500", description = "Eatery could not be created")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Eatery create(@RequestBody Eatery newEatery) {
        return eateryService.save(newEatery);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Eatery not found"),
            @ApiResponse(responseCode = "500", description = "Eatery could not be fetched")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Eatery get(@PathVariable Long id) {
        return eateryService.findById(id); // Direkte Rückgabe ohne orElseThrow
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Eatery could not be updated")
    })
    Eatery replace(@RequestBody Eatery newEatery, @PathVariable Long id) {
        return eateryService.update(id, newEatery); // Aufruf der Update-Methode in der Service-Schicht
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Eatery could not be deleted")
    })
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        eateryService.deleteById(id);
    }
}
