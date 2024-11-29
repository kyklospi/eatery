package com.eatery.api.controller;

import com.eatery.api.dto.UpdateUserRequest;
import com.eatery.exception.EateryManagerNotFoundException;
import com.eatery.entity.EateryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
class EateryManagerControllerIntegrationTest {
    @Autowired
    EateryManagerController eateryManagerController;

    private UpdateUserRequest managerRequest;

    @BeforeEach
    void setUp() {
        managerRequest = new UpdateUserRequest(
                "firstName",
                "lastName",
                "email",
                "phoneNumber"
        );
    }

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

    @Test
    void get() {
        // GIVEN
        EateryManager savedManager = eateryManagerController.create(managerRequest);
        Long savedManagerId = savedManager.getId();

        // WHEN
        EateryManager actual = eateryManagerController.get(savedManagerId);

        // THEN
        assertEquals(savedManagerId, actual.getId());
        assertEquals(savedManager.getFirstName(), actual.getFirstName());
        assertEquals(savedManager.getLastName(), actual.getLastName());
        assertEquals(savedManager.getEmail(), actual.getEmail());
        assertEquals(savedManager.getPhoneNumber(), actual.getPhoneNumber());
    }

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