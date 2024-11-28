package com.eatery.api.controller;

import com.eatery.api.dto.UpdateEateryRequest;
import com.eatery.entity.BusinessDayTime;
import com.eatery.entity.Eatery;
import com.eatery.exception.EateryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
class EateryControllerIntegrationTest {
    @Autowired
    EateryController eateryController;

    private UpdateEateryRequest eateryRequest;

    @BeforeEach
    void setUp() {
        eateryRequest = new UpdateEateryRequest(
                "RESTAURANT",
                "restaurantName",
                "restaurantAddress",
                "email",
                "phoneNumber",
                60,
                Set.of(
                        new BusinessDayTime(
                                DayOfWeek.SATURDAY,
                                LocalTime.of(15, 0),
                                LocalTime.of(23, 0)
                        )
                )
        );
    }

    @Test
    void create() {
        // WHEN
        Eatery actual = eateryController.create(eateryRequest);

        // THEN
        assertNotNull(actual.getId());
        assertEquals(eateryRequest.getType(), actual.getType().name());
        assertEquals(eateryRequest.getName(), actual.getName());
        assertEquals(eateryRequest.getAddress(), actual.getAddress());
        assertEquals(eateryRequest.getEmail(), actual.getEmail());
        assertEquals(eateryRequest.getPhoneNumber(), actual.getPhoneNumber());
        assertEquals(eateryRequest.getGuestCapacity(), actual.getGuestCapacity());
        assertEquals(eateryRequest.getBusinessDayTimes(), actual.getBusinessDayTimes());
    }

    @Test
    void getAll() {
        // GIVEN
        eateryController.create(eateryRequest);

        // WHEN
        List<Eatery> actual = eateryController.getAll();

        // THEN
        assertNotNull(actual);
        assertNotNull(actual.getLast().getId());
        assertEquals(eateryRequest.getType(), actual.getLast().getType().name());
        assertEquals(eateryRequest.getName(), actual.getLast().getName());
        assertEquals(eateryRequest.getAddress(), actual.getLast().getAddress());
        assertEquals(eateryRequest.getEmail(), actual.getLast().getEmail());
        assertEquals(eateryRequest.getPhoneNumber(), actual.getLast().getPhoneNumber());
        assertEquals(eateryRequest.getGuestCapacity(), actual.getLast().getGuestCapacity());
        assertEquals(eateryRequest.getBusinessDayTimes(), actual.getLast().getBusinessDayTimes());
    }

    @Test
    void search() {
        // WHEN
        List<Eatery> actual = eateryController.search("restaurantName", null, null);

        // THEN
        assertNotNull(actual);
        assertEquals("restaurantName", actual.getLast().getName());
        assertEquals("restaurantAddress", actual.getLast().getAddress());
        assertEquals("RESTAURANT", actual.getLast().getType().name());
    }

    @Test
    void get() {
        // GIVEN
        Eatery savedEatery = eateryController.create(eateryRequest);
        Long savedEateryId = savedEatery.getId();

        // WHEN
        Eatery actual = eateryController.get(savedEateryId);

        // THEN
        assertEquals(savedEatery, actual);
    }

    @Test
    void replace() {
        // GIVEN
        Eatery savedEatery = eateryController.create(eateryRequest);
        Long savedEateryId = savedEatery.getId();
        UpdateEateryRequest updateEateryRequest = new UpdateEateryRequest(
                "BAR",
                "updateName",
                "updateAddress",
                "updateEmail",
                "updatePhoneNumber",
                60,
                Set.of(
                        new BusinessDayTime(
                                DayOfWeek.SATURDAY,
                                LocalTime.of(15, 0),
                                LocalTime.of(23, 0)
                        )
                )
        );

        // WHEN
        Eatery actual = eateryController.replace(updateEateryRequest, savedEateryId);

        // THEN
        assertEquals(updateEateryRequest.getType(), actual.getType().name());
        assertEquals(updateEateryRequest.getName(), actual.getName());
        assertEquals(updateEateryRequest.getAddress(), actual.getAddress());
        assertEquals(updateEateryRequest.getEmail(), actual.getEmail());
        assertEquals(updateEateryRequest.getPhoneNumber(), actual.getPhoneNumber());
        assertEquals(updateEateryRequest.getGuestCapacity(), actual.getGuestCapacity());
        assertEquals(updateEateryRequest.getBusinessDayTimes(), actual.getBusinessDayTimes());
    }

    @Test
    void delete() {
        // GIVEN
        Eatery savedEatery = eateryController.create(eateryRequest);
        Long savedEateryId = savedEatery.getId();

        // WHEN
        eateryController.delete(savedEateryId);

        // THEN
        assertThrows(EateryNotFoundException.class, () -> eateryController.get(savedEateryId));
    }
}