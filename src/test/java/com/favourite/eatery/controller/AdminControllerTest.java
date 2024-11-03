package com.favourite.eatery.controller;

import com.favourite.eatery.dto.UpdateUserRequest;
import com.favourite.eatery.exception.AdminNotFoundException;
import com.favourite.eatery.model.Administrator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminControllerTest {
    @Autowired
    AdminController adminController;

    private UpdateUserRequest adminRequest;

    @BeforeEach
    void setUp() {
        adminRequest = new UpdateUserRequest(
                "firstName",
                "lastName",
                "email",
                "phoneNumber"
        );
    }

    @Test
    void create() {
        // WHEN
        Administrator actual = adminController.create(adminRequest);

        // THEN
        assertNotNull(actual.getId());
        assertEquals(adminRequest.getFirstName(), actual.getFirstName());
        assertEquals(adminRequest.getLastName(), actual.getLastName());
        assertEquals(adminRequest.getEmail(), actual.getEmail());
        assertEquals(adminRequest.getPhoneNumber(), actual.getPhoneNumber());
    }

    @Test
    void getAll() {
        // GIVEN
        adminController.create(adminRequest);

        // WHEN
        List<Administrator> actual = adminController.getAll();

        // THEN
        assertNotNull(actual);
        assertNotNull(actual.getLast().getId());
        assertEquals(adminRequest.getFirstName(), actual.getLast().getFirstName());
        assertEquals(adminRequest.getLastName(), actual.getLast().getLastName());
        assertEquals(adminRequest.getEmail(), actual.getLast().getEmail());
        assertEquals(adminRequest.getPhoneNumber(), actual.getLast().getPhoneNumber());
    }

    @Test
    void get() {
        // GIVEN
        Administrator savedAdmin = adminController.create(adminRequest);
        Long savedAdminId = savedAdmin.getId();

        // WHEN
        Administrator actual = adminController.get(savedAdminId);

        // THEN
        assertEquals(savedAdmin, actual);
    }

    @Test
    void replace() {
        // GIVEN
        Administrator savedAdmin = adminController.create(adminRequest);
        Long savedAdminId = savedAdmin.getId();
        UpdateUserRequest updateAdminRequest = new UpdateUserRequest(
                "updateFirstName",
                "updateLastName",
                "updateEmail",
                "updatePhone"
        );

        // WHEN
        Administrator actual = adminController.replace(updateAdminRequest, savedAdminId);

        // THEN
        assertEquals(updateAdminRequest.getFirstName(), actual.getFirstName());
        assertEquals(updateAdminRequest.getLastName(), actual.getLastName());
        assertEquals(updateAdminRequest.getEmail(), actual.getEmail());
        assertEquals(updateAdminRequest.getPhoneNumber(), actual.getPhoneNumber());
    }

    @Test
    void delete() {
        // GIVEN
        Administrator savedAdmin = adminController.create(adminRequest);
        Long savedAdminId = savedAdmin.getId();

        // WHEN
        adminController.delete(savedAdminId);

        // THEN
        assertThrows(AdminNotFoundException.class, () -> adminController.get(savedAdminId));
    }
}