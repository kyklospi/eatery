package com.eatery.api.service;

import com.eatery.exception.CustomerBadRequestException;
import com.eatery.exception.CustomerNotFoundException;
import com.eatery.entity.Customer;
import com.eatery.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for handling business logic related to customers.
 * This class interacts with the repository to perform CRUD operations on customer data.
 */
@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    /**
     * Retrieves all customers from the database.
     * @return A list of all customers.
     */
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    /**
     * Creates a new customer after validating the provided data.
     * @param newUser The customer object to be created.
     * @return The created customer object.
     * @throws CustomerBadRequestException if the provided customer data is invalid.
     */
    public Customer create(Customer newUser) {
        validateCustomer(newUser);
        return customerRepository.save(newUser);
    }

    /**
     * Deletes a customer by their ID.
     * @param id The ID of the customer to be deleted.
     * @throws CustomerNotFoundException if the customer with the specified ID does not exist.
     */
    public void deleteById(Long id) {
        Customer user = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.delete(user);
    }

    /**
     * Retrieves a customer by their ID.
     * @param id The ID of the customer to retrieve.
     * @return The customer object with the specified ID.
     * @throws CustomerNotFoundException if the customer with the specified ID does not exist.
     */
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    /**
     * Validates the customer data to ensure required fields are not null or empty.
     * @param customer The customer object to be validated.
     * @throws CustomerBadRequestException if any required field is missing or empty.
     */
    private void validateCustomer(Customer customer) {
        if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
            throw new CustomerBadRequestException("Email cannot be null or empty.");
        }
        if (customer.getFirstName() == null || customer.getFirstName().isEmpty()) {
            throw new CustomerBadRequestException("First Name cannot be null or empty.");
        }
        if (customer.getLastName() == null || customer.getLastName().isEmpty()) {
            throw new CustomerBadRequestException("Last Name cannot be null or empty.");
        }
        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().isEmpty()) {
            throw new CustomerBadRequestException("Phone Number cannot be null or empty.");
        }

    }



}