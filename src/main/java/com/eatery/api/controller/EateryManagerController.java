package com.eatery.api.controller;

import com.eatery.api.dto.UpdateUserRequest;
import com.eatery.entity.EateryManager;
import com.eatery.api.service.EateryManagerService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/managers")
public class EateryManagerController {
    @Autowired
    private EateryManagerService eateryManagerService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned"),
            @ApiResponse(responseCode = "404", description = "Eatery managers not found"),
            @ApiResponse(responseCode = "500", description = "Eatery managers could not be fetched")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<EateryManager> getAll() {
        return eateryManagerService.getAll();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Eatery manager could not be created")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EateryManager create(@RequestBody UpdateUserRequest newManager) {
        return eateryManagerService.create(newManager);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned"),
            @ApiResponse(responseCode = "404", description = "Eatery manager not found"),
            @ApiResponse(responseCode = "500", description = "Eatery manager could not be fetched")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    EateryManager get(@PathVariable Long id) {
        return eateryManagerService.get(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully modified"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Eatery manager not found"),
            @ApiResponse(responseCode = "500", description = "Eatery manager could not be updated")
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EateryManager replace(@RequestBody UpdateUserRequest newManager, @PathVariable Long id) {
        return eateryManagerService.replace(newManager, id);
    }

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
}
