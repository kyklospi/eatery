package com.eatery.api.controller;

import com.eatery.api.dto.UpdateManagerRequest;
import com.eatery.entity.BusinessDayTime;
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

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the EateryManagerController class.
 * This class tests the controller's functionality with real database interactions.
 */
@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
class EateryManagerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EateryManagerController eateryManagerController;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private UpdateManagerRequest managerRequest;

    /**
     * Set up method to initialize test data before each test.
     * Creates an instance of UpdateUserRequest for reuse in multiple tests.
     */
    @BeforeEach
    void setUp() {
        managerRequest = new UpdateManagerRequest(
                "firstName",
                "lastName",
                "userName",
                "password",
                1,
                "jobTitle",
                Set.of(
                        new BusinessDayTime(
                                DayOfWeek.MONDAY,
                                LocalTime.of(18, 0),
                                LocalTime.of(23, 0)
                        )
                )
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
        assertEquals(managerRequest.getUsername(), actual.getUsername());
        assertEquals(managerRequest.getPassword(), actual.getPassword());
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
        assertEquals(managerRequest.getUsername(), actual.getLast().getUsername());
        assertEquals(managerRequest.getPassword(), actual.getLast().getPassword());
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
        assertEquals(savedManager.getUsername(), actual.getUsername());
        assertEquals(savedManager.getPassword(), actual.getPassword());
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
        UpdateManagerRequest updateManagerRequest = new UpdateManagerRequest(
                "updateFirstName",
                "updateLastName",
                "userName",
                "password",
                1,
                "updateJobTitle",
                Set.of(
                        new BusinessDayTime(
                                DayOfWeek.MONDAY,
                                LocalTime.of(18, 0),
                                LocalTime.of(23, 0)
                        )
                )
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
        assertEquals(updateManagerRequest.getUsername(), actual.getUsername());
        assertEquals(updateManagerRequest.getPassword(), actual.getPassword());
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

    /**
     * Test retrieving a specific EateryManager by username and password.
     * Verifies that the correct EateryManager is returned based on the username and password.
     */
    @Test
    void login() throws Exception {
        // GIVEN
        EateryManager savedManager = eateryManagerController.create(managerRequest);

        // WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/managers/login")
                                .param("username", savedManager.getUsername())
                                .param("password", savedManager.getPassword())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        EateryManager actual = MAPPER.readValue(result.getResponse().getContentAsString(), EateryManager.class);

        // THEN
        assertEquals(savedManager.getFirstName(), actual.getFirstName());
        assertEquals(savedManager.getLastName(), actual.getLastName());
        assertEquals(savedManager.getUsername(), actual.getUsername());
        assertEquals(savedManager.getPassword(), actual.getPassword());
    }
}