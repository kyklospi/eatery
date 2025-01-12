package com.eatery.api.controller;

import com.eatery.api.dto.CreateReservationRequest;
import com.eatery.api.dto.UpdateReservationRequest;
import com.eatery.entity.EateryManager;
import com.eatery.entity.Reservation;
import com.eatery.entity.ReservationHistory;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the ReservationController class.
 * This class tests the controller's functionality with real database interactions.
 */
@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
class ReservationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ReservationController reservationController;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private CreateReservationRequest createRequest;
    private UpdateReservationRequest updateRequest;


    /**
     * Set up method to initialize test data before each test.
     */
    @BeforeEach
    void setUp() {
        createRequest = new CreateReservationRequest(
                1L, // Customer ID
                1L, // Eatery ID
                LocalDateTime.of(LocalDate.of(2024,12,24), LocalTime.of(18,0)),
                4 // Guest number
        );

        updateRequest = new UpdateReservationRequest();
        updateRequest.setDateTime(LocalDateTime.of(LocalDate.of(2024,12,24), LocalTime.of(23,0)));
        updateRequest.setGuestNumber(6);

    }

    /**
     * Test the creation of a new Reservation.
     * Verifies that the new Reservation is correctly created with the expected values.
     */
    @Test
    void create() throws Exception {
        // WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/reservations")
                                .content(MAPPER.writeValueAsString(createRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        Reservation actual = MAPPER.readValue(result.getResponse().getContentAsString(), Reservation.class);

        // THEN
        assertNotNull(actual.getId());
        assertEquals(createRequest.getCustomerId(), actual.getCustomerId());
        assertEquals(createRequest.getEateryId(), actual.getEateryId());
        assertEquals(createRequest.getReservationDateTime(), actual.getReservationDateTime());
        assertEquals(createRequest.getGuestNumber(), actual.getGuestNumber());
    }

    /**
     * Test retrieving all Reservations.
     * Ensures that the created Reservation is included in the list returned by the controller.
     */
    @Test
    void getAll() throws Exception {
       //WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List<Reservation> actual = MAPPER.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        // THEN
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
        //assertEquals(createRequest.getFirstName(), actual.getLast().getFirstName());
        //assertEquals(managerRequest.getLastName(), actual.getLast().getLastName());
        //assertEquals(managerRequest.getEmail(), actual.getLast().getEmail());
        //assertEquals(managerRequest.getPhoneNumber(), actual.getLast().getPhoneNumber());
    }

    /**
     * Test retrieving a specific Reservation by ID.
     * Verifies that the correct Reservation is returned based on the ID.
     */
    @Test
    void get() throws Exception {
        // GIVEN
        CreateReservationRequest createRequest2 = new CreateReservationRequest(
                1L, // Customer ID
                1L, // Eatery ID
                LocalDateTime.of(LocalDate.of(2024,12,24), LocalTime.of(19,0)),
                4 // Guest number
        );
        Reservation savedReservation = reservationController.create(createRequest2);
        Long savedReservationID = savedReservation.getId();

        // WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/reservations/{id}", savedReservationID)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Reservation actual = MAPPER.readValue(result.getResponse().getContentAsString(), Reservation.class);

        // THEN
        assertEquals(savedReservation.getId(), actual.getId());
        assertEquals(savedReservation.getCustomerId(), actual.getCustomerId());
        assertEquals(savedReservation.getEateryId(), actual.getEateryId());
        assertEquals(savedReservation.getReservationDateTime(), actual.getReservationDateTime());
    }

    /**
     * Test updating an existing Reservation.
     * Ensures that the Reservation's properties are correctly updated.
     */
    @Test
    void replace() throws Exception {
        // GIVEN
        MvcResult createResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/reservations")
                        .content(MAPPER.writeValueAsString(createRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        Reservation createdReservation = MAPPER.readValue(createResult.getResponse().getContentAsString(), Reservation.class);

        // WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/reservations/{id}", createdReservation.getId())
                                .content(MAPPER.writeValueAsString(updateRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Reservation actual = MAPPER.readValue(result.getResponse().getContentAsString(), Reservation.class);

        // THEN
        assertEquals(createdReservation.getId(), actual.getId());
        assertEquals(updateRequest.getDateTime(), actual.getReservationDateTime());
        assertEquals(updateRequest.getGuestNumber(), actual.getGuestNumber());
    }

    /**
     * Test deleting an existing Reservation.
     * Ensures that the Reservation is successfully deleted.
     */
    @Test
    void delete() throws Exception {
        // GIVEN
        MvcResult createResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/reservations")
                        .content(MAPPER.writeValueAsString(createRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        Reservation createdReservation = MAPPER.readValue(createResult.getResponse().getContentAsString(), Reservation.class);

        // WHEN
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/reservations/{id}", createdReservation.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

        // THEN
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/reservations/{id}", createdReservation.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    /**
     * Test retrieving reservation history by reservation ID.
     * Ensures that the history is returned correctly.
     */
    @Test
    void history() throws Exception {
        // GIVEN
        MvcResult createResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/reservations")
                        .content(MAPPER.writeValueAsString(createRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        Reservation createdReservation = MAPPER.readValue(createResult.getResponse().getContentAsString(), Reservation.class);

        // WHEN
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/reservations/{id}/history", createdReservation.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List<ReservationHistory> actual = MAPPER.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        //THEN
        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        //assertThrows(ReservationNotFoundException.class, () -> ReservationController.get(ReservationId));
 }
}
