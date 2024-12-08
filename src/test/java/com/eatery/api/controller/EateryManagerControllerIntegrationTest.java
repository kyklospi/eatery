package com.eatery.api.controller;

import com.eatery.api.dto.UpdateUserRequest;
import com.eatery.exception.EateryManagerNotFoundException;
import com.eatery.entity.EateryManager;
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
 * Integration tests for the EateryManagerController class.
 * This class tests the controller's functionality with real database interactions.
 */
@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
class EateryManagerControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EateryManagerController eateryManagerController;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private UpdateUserRequest managerRequest;

    /**
     * Set up method to initialize test data before each test.
     * Creates an instance of UpdateUserRequest for reuse in multiple tests.
     */
    @BeforeEach
    void setUp() {
        managerRequest = new UpdateUserRequest(
                "firstName",
                "lastName",
                "email",
                "phoneNumber"
        );
    }

    /**
     * Test the creation of a new EateryManager.
     * Verifies that the new EateryManager is correctly created with the expected values.
     */
    @Test
    void create() throws Exception {
        // WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/managers")
                                .content(
                                        MAPPER.writeValueAsString(managerRequest)
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        EateryManager actual = MAPPER.readValue(result.getResponse().getContentAsString(), EateryManager.class);

        // THEN
        assertNotNull(actual.getId());
        assertEquals(managerRequest.getFirstName(), actual.getFirstName());
        assertEquals(managerRequest.getLastName(), actual.getLastName());
        assertEquals(managerRequest.getEmail(), actual.getEmail());
        assertEquals(managerRequest.getPhoneNumber(), actual.getPhoneNumber());
    }

    /**
     * Test retrieving all EateryManagers.
     * Ensures that the created EateryManager is included in the list returned by the controller.
     */
    @Test
    void getAll() throws Exception {
        // GIVEN
        eateryManagerController.create(managerRequest);

        // WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/managers")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List<EateryManager> actual = MAPPER.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        // THEN
        assertNotNull(actual);
        assertNotNull(actual.getLast().getId());
        assertEquals(managerRequest.getFirstName(), actual.getLast().getFirstName());
        assertEquals(managerRequest.getLastName(), actual.getLast().getLastName());
        assertEquals(managerRequest.getEmail(), actual.getLast().getEmail());
        assertEquals(managerRequest.getPhoneNumber(), actual.getLast().getPhoneNumber());
    }

    /**
     * Test retrieving a specific EateryManager by ID.
     * Verifies that the correct EateryManager is returned based on the ID.
     */
    @Test
    void get() throws Exception {
        // GIVEN
        EateryManager savedManager = eateryManagerController.create(managerRequest);
        Long savedManagerId = savedManager.getId();

        // WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/managers/{id}", savedManagerId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        EateryManager actual = MAPPER.readValue(result.getResponse().getContentAsString(), EateryManager.class);

        // THEN
        assertEquals(savedManagerId, actual.getId());
        assertEquals(savedManager.getFirstName(), actual.getFirstName());
        assertEquals(savedManager.getLastName(), actual.getLastName());
        assertEquals(savedManager.getEmail(), actual.getEmail());
        assertEquals(savedManager.getPhoneNumber(), actual.getPhoneNumber());
    }

    /**
     * Test updating an existing EateryManager.
     * Ensures that the EateryManager's properties are correctly updated.
     */
    @Test
    void replace() throws Exception {
        // GIVEN
        EateryManager savedManager = eateryManagerController.create(managerRequest);
        Long savedManagerId = savedManager.getId();
        UpdateUserRequest updateManagerRequest = new UpdateUserRequest(
                "updateFirstName",
                "updateLastName",
                "updateEmail",
                "updatePhone"
        );

        // WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/managers/{id}", savedManagerId)
                                .content(
                                        MAPPER.writeValueAsString(updateManagerRequest)
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        EateryManager actual = MAPPER.readValue(result.getResponse().getContentAsString(), EateryManager.class);

        // THEN
        assertEquals(updateManagerRequest.getFirstName(), actual.getFirstName());
        assertEquals(updateManagerRequest.getLastName(), actual.getLastName());
        assertEquals(updateManagerRequest.getEmail(), actual.getEmail());
        assertEquals(updateManagerRequest.getPhoneNumber(), actual.getPhoneNumber());
    }

    /**
     * Test deleting an existing EateryManager.
     * Verifies that after deletion, an exception is thrown when trying to retrieve the deleted manager.
     */
    @Test
    void delete() throws Exception {
        // GIVEN
        EateryManager savedManager = eateryManagerController.create(managerRequest);
        Long savedManagerId = savedManager.getId();

        // WHEN
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/managers/{id}", savedManagerId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andReturn();

        // THEN
        assertThrows(EateryManagerNotFoundException.class, () -> eateryManagerController.get(savedManagerId));
    }
}