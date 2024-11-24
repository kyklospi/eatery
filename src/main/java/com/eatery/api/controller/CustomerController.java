package com.eatery.api.controller;

import com.eatery.api.dto.UpdateUserRequest;
import com.eatery.api.service.CustomerService;
import com.eatery.entity.Customer;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Customers not found"),
            @ApiResponse(responseCode = "500", description = "Customers could not be fetched")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Customer> getAll() {
        return customerService.getAll();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Customer could not be created")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Customer create(@RequestBody Customer newCustomer) {
        return customerService.create(newCustomer);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Customer could not be fetched")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Customer get(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Customer could not be updated")
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Customer replace(@RequestBody UpdateUserRequest newCustomer, @PathVariable Long id) {
        return customerService.findById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Customer could not be deleted")
    })
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        customerService.deleteById(id);
    }

}
