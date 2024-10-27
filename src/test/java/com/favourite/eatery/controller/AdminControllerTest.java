package com.favourite.eatery.controller;

import com.favourite.eatery.model.Administrator;
import com.favourite.eatery.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class AdminControllerTest {
    @Autowired
    AdminController adminController;
    @MockBean
    AdminService adminMockService;

    private Administrator testAdmin;

    @BeforeEach
    void setUp() {
        testAdmin = new Administrator(
                "firstName",
                "lastName",
                "email",
                "phoneNumber"
        );
    }

    @Test
    void getAll() {
        // GIVEN
        List<Administrator> expected = List.of(testAdmin);
        when(adminMockService.getAll()).thenReturn(expected);

        // WHEN
        List<Administrator> actual = adminController.getAll();

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    void create() {
    }

    @Test
    void get() {
    }

    @Test
    void replace() {
    }

    @Test
    void delete() {
    }
}