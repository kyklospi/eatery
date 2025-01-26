package com.eatery.api.controller;

import com.eatery.api.dto.UpdateCustomerRequest;
import com.eatery.service.CustomerService;
import com.eatery.entity.Customer;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling customer-related operations, including
 * fetching all customers, creating a new customer, updating a customer,
 * retrieving a customer by ID, and deleting a customer.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * Fetches all customers from the database.
     * @return A list of all customers.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned"),
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
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Customer could not be created")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Customer create(@RequestBody UpdateCustomerRequest newCustomer) {
        return customerService.create(newCustomer);
    }

    /**
     * Fetches a customer by their ID.
     * @param id The ID of the customer to retrieve.
     * @return The customer with the specified ID.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned"),
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
            @ApiResponse(responseCode = "200", description = "Successfully modified"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Customer could not be updated")
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Customer replace(@RequestBody UpdateCustomerRequest newCustomer, @PathVariable Long id) {
        return customerService.replace(newCustomer, id);
    }

    /**
     * Deletes the customer with the specified ID.
     * @param id The ID of the customer to delete.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Customer could not be deleted")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        customerService.delete(id);
    }

    /**
     * Fetches a customer by their username and password.
     * @param username The username of the customer to retrieve.
     * @param password The password of the customer to retrieve.
     * @return The customer with the specified username and password.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Customer could not be fetched")
    })
    @GetMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    Customer login(String username, String password) {
        return customerService.get(username, password);
    }
}
