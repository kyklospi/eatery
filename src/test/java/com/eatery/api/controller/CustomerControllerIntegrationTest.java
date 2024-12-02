package com.eatery.api.controller;

import com.eatery.api.dto.UpdateUserRequest;
import com.eatery.entity.Customer;
import com.eatery.exception.CustomerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the CustomerController class.
 * This class tests the controller's functionality with real database interactions.
 */
@SpringBootTest
class CustomerControllerIntegrationTest {
    @Autowired
    CustomerController customerController;

    private UpdateUserRequest customerRequest;

    /**
     * Set up method to initialize test data before each test.
     * Creates an instance of UpdateUserRequest for reuse in multiple tests.
     */
    @BeforeEach
    void setUp() {
        customerRequest = new UpdateUserRequest(
                "John",
                "Doe",
                "john.doe@example.com",
                "123456789"
        );
    }

    /**
     * Test the creation of a new Customer.
     * Verifies that the new Customer is correctly created with the expected values.
     */
    @Test
    void create() {
        // WHEN
        Customer actual = customerController.create(new Customer(
                customerRequest.getFirstName(),
                customerRequest.getLastName(),
                customerRequest.getEmail(),
                customerRequest.getPhoneNumber()
        ));

        // THEN
        assertNotNull(actual.getId());
        assertEquals(customerRequest.getFirstName(), actual.getFirstName());
        assertEquals(customerRequest.getLastName(), actual.getLastName());
        assertEquals(customerRequest.getEmail(), actual.getEmail());
        assertEquals(customerRequest.getPhoneNumber(), actual.getPhoneNumber());
    }

    /**
     * Test retrieving all Customers.
     * Ensures that the created Customer is included in the list returned by the controller.
     */
    @Test
    void getAll() {
        // GIVEN
        customerController.create(new Customer(
                customerRequest.getFirstName(),
                customerRequest.getLastName(),
                customerRequest.getEmail(),
                customerRequest.getPhoneNumber()
        ));

        // WHEN
        List<Customer> actual = customerController.getAll();

        // THEN
        assertNotNull(actual);
        Customer lastCustomer = actual.get(actual.size() - 1);
        assertNotNull(lastCustomer.getId());
        assertEquals(customerRequest.getFirstName(), lastCustomer.getFirstName());
        assertEquals(customerRequest.getLastName(), lastCustomer.getLastName());
        assertEquals(customerRequest.getEmail(), lastCustomer.getEmail());
        assertEquals(customerRequest.getPhoneNumber(), lastCustomer.getPhoneNumber());
    }

    /**
     * Test retrieving a specific Customer by ID.
     * Verifies that the correct Customer is returned based on the ID.
     */
    @Test
    void get() {
        // GIVEN
        Customer savedCustomer = customerController.create(new Customer(
                customerRequest.getFirstName(),
                customerRequest.getLastName(),
                customerRequest.getEmail(),
                customerRequest.getPhoneNumber()
        ));
        Long savedCustomerId = savedCustomer.getId();

        // WHEN
        Customer actual = customerController.get(savedCustomerId);

        // THEN
        assertEquals(savedCustomer, actual);
    }

    /**
     * Test updating an existing Customer.
     * Ensures that the Customer's properties are correctly updated.
     */
    @Test
    void replace() {
        // GIVEN
        Customer savedCustomer = customerController.create(new Customer(
                customerRequest.getFirstName(),
                customerRequest.getLastName(),
                customerRequest.getEmail(),
                customerRequest.getPhoneNumber()
        ));
        Long savedCustomerId = savedCustomer.getId();
        UpdateUserRequest updateCustomerRequest = new UpdateUserRequest(
                "Jane",
                "Smith",
                "jane.smith@example.com",
                "987654321"
        );

        // WHEN
        Customer actual = customerController.replace(updateCustomerRequest, savedCustomerId);

        // THEN
        assertEquals(updateCustomerRequest.getFirstName(), actual.getFirstName());
        assertEquals(updateCustomerRequest.getLastName(), actual.getLastName());
        assertEquals(updateCustomerRequest.getEmail(), actual.getEmail());
        assertEquals(updateCustomerRequest.getPhoneNumber(), actual.getPhoneNumber());
    }

    /**
     * Test deleting an existing Customer.
     * Verifies that after deletion, an exception is thrown when trying to retrieve the deleted customer.
     */
    @Test
    void delete() {
        // GIVEN
        Customer savedCustomer = customerController.create(new Customer(
                customerRequest.getFirstName(),
                customerRequest.getLastName(),
                customerRequest.getEmail(),
                customerRequest.getPhoneNumber()
        ));
        Long savedCustomerId = savedCustomer.getId();

        // WHEN
        customerController.delete(savedCustomerId);

        // THEN
        assertThrows(CustomerNotFoundException.class, () -> customerController.get(savedCustomerId));
    }
}
