package com.eatery.service;

import com.eatery.api.dto.UpdateCustomerRequest;
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
     * @param newCustomerRequest The customer object to be created.
     * @return The created customer object.
     * @throws CustomerBadRequestException if the provided customer data is invalid.
     */
    public Customer create(UpdateCustomerRequest newCustomerRequest) {
        validateCustomer(newCustomerRequest);
        Customer customer = new Customer(
                newCustomerRequest.getFirstName(),
                newCustomerRequest.getLastName(),
                newCustomerRequest.getEmail(),
                newCustomerRequest.getPhoneNumber(),
                newCustomerRequest.getPaymentMethod()
        );
        return customerRepository.save(customer);
    }

    /**
     * Deletes a customer by their ID.
     * @param id The ID of the customer to be deleted.
     * @throws CustomerNotFoundException if the customer with the specified ID does not exist.
     */
    public void delete(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        customerRepository.delete(customer);
    }

    /**
     * Retrieves a customer by their ID.
     * @param id The ID of the customer to retrieve.
     * @return The customer object with the specified ID.
     * @throws CustomerNotFoundException if the customer with the specified ID does not exist.
     */
    public Customer get(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
    }

    /**
     * Validates the customer data to ensure required fields are not null or empty.
     * @param newCustomer The customer object to be validated.
     * @throws CustomerBadRequestException if any required field is missing or empty.
     */
    public Customer replace(UpdateCustomerRequest newCustomer, Long id) {
        validateCustomer(newCustomer);
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setFirstName(newCustomer.getFirstName());
                    customer.setLastName(newCustomer.getLastName());
                    customer.setEmail(newCustomer.getEmail());
                    customer.setPhoneNumber(newCustomer.getPhoneNumber());
                    customer.setPayment(newCustomer.getPaymentMethod());
                    return customerRepository.save(customer);
                })
                .orElseThrow(CustomerNotFoundException::new);
    }

    private void validateCustomer(UpdateCustomerRequest customer) {
        if (customer == null) {
            throw new CustomerBadRequestException("Customer request must not be null.");
        }
        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            throw new CustomerBadRequestException("Email must not be null or empty.");
        }
        if (customer.getFirstName() == null || customer.getFirstName().isBlank()) {
            throw new CustomerBadRequestException("First Name must not be null or empty.");
        }
        if (customer.getLastName() == null || customer.getLastName().isBlank()) {
            throw new CustomerBadRequestException("Last Name must not be null or empty.");
        }
        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().isBlank()) {
            throw new CustomerBadRequestException("Phone Number must not be null or empty.");
        }
    }
}