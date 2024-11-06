package com.favourite.eatery.controller;

import com.favourite.eatery.dto.UpdateUserRequest;
import com.favourite.eatery.exception.CustomerNotFoundException;
import com.favourite.eatery.model.Eatery;
import com.favourite.eatery.model.Customer;
import com.favourite.eatery.repository.CustomerRepository;
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
    private CustomerRepository repository;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Customers not found"),
            @ApiResponse(responseCode = "500", description = "Customers could not be fetched")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Customer> getAll() {
        return repository.findAll();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Customer could not be created")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Customer create(@RequestBody Customer newCustomer) {
        return repository.save(newCustomer);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Customer could not be fetched")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Customer get(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Customer could not be updated")
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Customer replace(@RequestBody UpdateUserRequest newCustomer, @PathVariable Long id) {
        return repository.findById(id)
                .map(customer -> {
                    customer.setFirstName(newCustomer.getFirstName());
                    customer.setLastName(newCustomer.getLastName());
                    customer.setEmail(newCustomer.getEmail());
                    customer.setPhoneNumber(newCustomer.getPhoneNumber());
                    return repository.save(customer);
                })
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Customer could not be deleted")
    })
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Customer favourite could not be added")
    })
    @PutMapping(path = "/{customerId}/favourites", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Customer addCustomerFavourite(@RequestBody Eatery newEatery, @PathVariable Long customerId) {
        return repository.findById(customerId)
                .map(customer -> {
                    customer.getFavouriteEateries().add(newEatery);
                    return repository.save(customer);
                })
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Customer favourite could not be deleted")
    })
    @DeleteMapping(path = "/{customerId}/favourites/{eateryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    Customer deleteCustomerFavourite(@PathVariable Long customerId, @PathVariable Long eateryId) {
        Customer customer = repository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        customer.getFavouriteEateries().removeIf(
                favourite -> favourite.getId().equals(eateryId)
        );
        return customer;
    }
}
