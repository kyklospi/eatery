package com.eatery.api.controller;

import com.eatery.api.dto.UpdateUserRequest;
import com.eatery.exception.EateryManagerNotFoundException;
import com.eatery.entity.EateryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the EateryManagerController class.
 * This class tests the controller's functionality with real database interactions.
 */
@SpringBootTest
class EateryManagerControllerIntegrationTest {
    @Autowired
    EateryManagerController eateryManagerController;

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
    void create() {
        // WHEN
        EateryManager actual = eateryManagerController.create(managerRequest);

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
    void getAll() {
        // GIVEN
        eateryManagerController.create(managerRequest);

        // WHEN
        List<EateryManager> actual = eateryManagerController.getAll();

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
    void get() {
        // GIVEN
        EateryManager savedManager = eateryManagerController.create(managerRequest);
        Long savedManagerId = savedManager.getId();

        // WHEN
        EateryManager actual = eateryManagerController.get(savedManagerId);

        // THEN
        assertEquals(savedManager, actual);
    }

    /**
     * Test updating an existing EateryManager.
     * Ensures that the EateryManager's properties are correctly updated.
     */
    @Test
    void replace() {
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
        EateryManager actual = eateryManagerController.replace(updateManagerRequest, savedManagerId);

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
    void delete() {
        // GIVEN
        EateryManager savedManager = eateryManagerController.create(managerRequest);
        Long savedManagerId = savedManager.getId();

        // WHEN
        eateryManagerController.delete(savedManagerId);

        // THEN
        assertThrows(EateryManagerNotFoundException.class, () -> eateryManagerController.get(savedManagerId));
    }
}