package com.favourite.eatery.controller;

import com.favourite.eatery.dto.UpdateUserRequest;
import com.favourite.eatery.model.Administrator;
import com.favourite.eatery.service.AdminService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admins")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Admins not found"),
            @ApiResponse(responseCode = "500", description = "Admins could not be fetched")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Administrator> getAll() {
        return adminService.getAll();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Admin could not be created")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Administrator create(@RequestBody UpdateUserRequest newAdmin) {
        return adminService.create(newAdmin);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Admin not found"),
            @ApiResponse(responseCode = "500", description = "Admin could not be fetched")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Administrator get(@PathVariable Long id) {
        return adminService.get(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Admin not found"),
            @ApiResponse(responseCode = "500", description = "Admin could not be updated")
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Administrator replace(@RequestBody UpdateUserRequest newAdmin, @PathVariable Long id) {
        return adminService.replace(newAdmin, id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Admin could not be deleted")
    })
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        adminService.delete(id);
    }
}
