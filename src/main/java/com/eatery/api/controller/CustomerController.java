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

/**
 * Controller for handling customer-related operations, including
 * fetching all customers, creating a new customer, updating a customer,
 * retrieving a customer by ID, and deleting a customer.
 */
@RestController
@RequestMapping(path = "/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * Fetches all customers from the database.
     * @return A list of all customers.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Customers not found"),
            @ApiResponse(responseCode = "500", description = "Customers could not be fetched")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Customer> getAll() {
        return customerService.getAll();
    }

    /**
     * Creates a new customer based on the provided details.
     * @param newCustomer The customer data to create a new customer.
     * @return The created customer.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Customer could not be created")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Customer create(@RequestBody UpdateUserRequest newCustomer) {
        return customerService.create(newCustomer);
    }

    /**
     * Fetches a customer by their ID.
     * @param id The ID of the customer to retrieve.
     * @return The customer with the specified ID.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Customer could not be fetched")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Customer get(@PathVariable Long id) {
        return customerService.get(id);
    }

    /**
     * Updates an existing customer with the provided new customer details.
     * @param newCustomer The new details for the customer.
     * @param id The ID of the customer to update.
     * @return The updated customer.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Customer could not be updated")
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Customer replace(@RequestBody UpdateUserRequest newCustomer, @PathVariable Long id) {
        return customerService.replace(newCustomer, id);
    }

    /**
     * Deletes the customer with the specified ID.
     * @param id The ID of the customer to delete.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Customer could not be deleted")
    })
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        customerService.delete(id);
    }
}
