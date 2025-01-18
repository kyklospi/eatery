package com.eatery.api.controller;

import com.eatery.api.dto.UpdateCustomerRequest;
import com.eatery.entity.Customer;
import com.eatery.entity.PaymentMethod;
import com.eatery.exception.CustomerNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the CustomerController class.
 * This class tests the controller's functionality with real database interactions.
 */
@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
class CustomerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CustomerController customerController;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private UpdateCustomerRequest customerRequest;

    /**
     * Set up method to initialize test data before each test.
     * Creates an instance of UpdateUserRequest for reuse in multiple tests.
     */
    @BeforeEach
    void setUp() {
        customerRequest = new UpdateCustomerRequest(
                "firstName",
                "lastName",
                "email",
                "phoneNumber",
                PaymentMethod.CASH
        );
    }

    @Test
    void create() throws Exception {
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/customers")
                                .content(
                                        MAPPER.writeValueAsString(customerRequest)
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        Customer actual = MAPPER.readValue(result.getResponse().getContentAsString(), Customer.class);

        assertNotNull(actual.getId());
        assertEquals(customerRequest.getFirstName(), actual.getFirstName());
        assertEquals(customerRequest.getLastName(), actual.getLastName());
        assertEquals(customerRequest.getEmail(), actual.getEmail());
        assertEquals(customerRequest.getPhoneNumber(), actual.getPhoneNumber());
    }

    @Test
    void getAll() throws Exception {
        customerController.create(customerRequest);

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List<Customer> actual = MAPPER.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertNotNull(actual);
        assertNotNull(actual.getLast().getId());
        assertEquals(customerRequest.getFirstName(), actual.getLast().getFirstName());
        assertEquals(customerRequest.getLastName(), actual.getLast().getLastName());
        assertEquals(customerRequest.getEmail(), actual.getLast().getEmail());
        assertEquals(customerRequest.getPhoneNumber(), actual.getLast().getPhoneNumber());
    }

    @Test
    void get() throws Exception {
        Customer savedCustomer = customerController.create(customerRequest);
        Long savedCustomerId = savedCustomer.getId();

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/customers/{id}", savedCustomerId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Customer actual = MAPPER.readValue(result.getResponse().getContentAsString(), Customer.class);

        assertEquals(savedCustomerId, actual.getId());
        assertEquals(savedCustomer.getFirstName(), actual.getFirstName());
        assertEquals(savedCustomer.getLastName(), actual.getLastName());
        assertEquals(savedCustomer.getEmail(), actual.getEmail());
        assertEquals(savedCustomer.getPhoneNumber(), actual.getPhoneNumber());
    }

    @Test
    void replace() throws Exception {
        Customer savedCustomer = customerController.create(customerRequest);
        Long savedCustomerId = savedCustomer.getId();
        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest(
                "updateFirstName",
                "updateLastName",
                "updateEmail",
                "updatePhone",
                PaymentMethod.CASH
        );

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/customers/{id}", savedCustomerId)
                                .content(
                                        MAPPER.writeValueAsString(updateCustomerRequest)
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Customer actual = MAPPER.readValue(result.getResponse().getContentAsString(), Customer.class);

        assertEquals(updateCustomerRequest.getFirstName(), actual.getFirstName());
        assertEquals(updateCustomerRequest.getLastName(), actual.getLastName());
        assertEquals(updateCustomerRequest.getEmail(), actual.getEmail());
        assertEquals(updateCustomerRequest.getPhoneNumber(), actual.getPhoneNumber());
    }

    @Test
    void delete() throws Exception {
        Customer savedCustomer = customerController.create(customerRequest);
        Long savedCustomerId = savedCustomer.getId();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/customers/{id}", savedCustomerId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andReturn();

        assertThrows(CustomerNotFoundException.class, () -> customerController.get(savedCustomerId));
    }
}
