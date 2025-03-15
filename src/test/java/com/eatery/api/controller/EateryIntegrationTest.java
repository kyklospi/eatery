package com.eatery.api.controller;

import com.eatery.api.dto.UpdateEateryRequest;
import com.eatery.entity.BusinessDayTime;
import com.eatery.entity.Eatery;
import com.eatery.exception.EateryNotFoundException;
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

@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
class EateryIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EateryController eateryController;

    private static final ObjectMapper MAPPER = new ObjectMapper();
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
    void create() throws Exception {
        // WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/eateries")
                                .content(
                                        MAPPER.writeValueAsString(eateryRequest)
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        Eatery actual = MAPPER.readValue(result.getResponse().getContentAsString(), Eatery.class);

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
    void getAll() throws Exception {
        // GIVEN
        eateryController.create(eateryRequest);

        // WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/eateries")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List<Eatery> actual = MAPPER.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

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
    void search() throws Exception {
        // GIVEN
        eateryController.create(eateryRequest);

        // WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/eateries/search")
                                .param("name", "restaurantName")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List<Eatery> actual = MAPPER.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        // THEN
        assertNotNull(actual);
        assertEquals("restaurantName", actual.getLast().getName());
        assertEquals("restaurantAddress", actual.getLast().getAddress());
        assertEquals("RESTAURANT", actual.getLast().getType().name());
    }

    @Test
    void get() throws Exception {
        // GIVEN
        Eatery savedEatery = eateryController.create(eateryRequest);
        Long savedEateryId = savedEatery.getId();

        // WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/eateries/{id}", savedEateryId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Eatery actual = MAPPER.readValue(result.getResponse().getContentAsString(), Eatery.class);

        // THEN
        assertEquals(savedEatery, actual);
    }

    @Test
    void replace() throws Exception {
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
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/eateries/{id}", savedEateryId)
                                .content(
                                        MAPPER.writeValueAsString(updateEateryRequest)
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Eatery actual = MAPPER.readValue(result.getResponse().getContentAsString(), Eatery.class);

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
    void delete() throws Exception {
        // GIVEN
        Eatery savedEatery = eateryController.create(eateryRequest);
        Long savedEateryId = savedEatery.getId();

        // WHEN
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/eateries/{id}", savedEateryId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andReturn();

        // THEN
        assertThrows(EateryNotFoundException.class, () -> eateryController.get(savedEateryId));
    }
}