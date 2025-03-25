package com.eatery.api.controller;

import com.eatery.api.dto.CreateReservationRequest;
import com.eatery.api.dto.UpdateReservationRequest;
import com.eatery.entity.Reservation;
import com.eatery.entity.ReservationHistory;
import com.eatery.exception.ReservationNotFoundException;
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
import java.time.Month;
import java.util.List;

import static com.eatery.entity.Reservation.Status.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
public class ReservationIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ReservationController reservationController;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private CreateReservationRequest createReservationRequest;

    @BeforeEach
    void setUp() {
        createReservationRequest = new CreateReservationRequest(
                1,
                1,
                LocalDateTime.of(
                        LocalDate.of(2025, Month.DECEMBER, 29),
                        LocalTime.of(19, 0)
                ),
                4
        );
    }

    @Test
    void create() throws Exception {
        // WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/reservations")
                                .content(
                                        MAPPER.writeValueAsString(createReservationRequest)
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        Reservation actual = MAPPER.readValue(result.getResponse().getContentAsString(), Reservation.class);

        // THEN
        assertNotNull(actual.getId());
        assertEquals(createReservationRequest.getCustomerId(), actual.getCustomerId());
        assertEquals(createReservationRequest.getEateryId(), actual.getEateryId());
        assertEquals(createReservationRequest.getGuestNumber(), actual.getGuestNumber());
        assertEquals(createReservationRequest.getReservationDateTime(), actual.getReservationDateTime());
        assertEquals(CONFIRMED, actual.getStatus());
    }

    @Test
    void getAll() throws Exception {
        // GIVEN
        reservationController.create(createReservationRequest);

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
        assertNotNull(actual.getLast().getId());
        assertEquals(createReservationRequest.getCustomerId(), actual.getLast().getCustomerId());
        assertEquals(createReservationRequest.getEateryId(), actual.getLast().getEateryId());
        assertEquals(createReservationRequest.getGuestNumber(), actual.getLast().getGuestNumber());
        assertEquals(createReservationRequest.getReservationDateTime(), actual.getLast().getReservationDateTime());
        assertEquals(CONFIRMED, actual.getLast().getStatus());
    }

    @Test
    void get() throws Exception {
        // GIVEN
        Reservation savedReservation = reservationController.create(createReservationRequest);
        Long savedReservationId = savedReservation.getId();

        // WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/reservations/{id}", savedReservationId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Reservation actual = MAPPER.readValue(result.getResponse().getContentAsString(), Reservation.class);

        // THEN
        assertEquals(savedReservation, actual);
    }

    @Test
    void replace() throws Exception {
        Reservation savedReservation = reservationController.create(createReservationRequest);
        Long savedReservationId = savedReservation.getId();
        UpdateReservationRequest updateReservationRequest = new UpdateReservationRequest(
                LocalDateTime.of(
                        LocalDate.of(2025, Month.DECEMBER, 22),
                        LocalTime.of(19, 30)
                ),
                2
        );

        // WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/reservations/{id}", savedReservationId)
                                .content(
                                        MAPPER.writeValueAsString(updateReservationRequest)
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Reservation actual = MAPPER.readValue(result.getResponse().getContentAsString(), Reservation.class);

        // THEN
        assertEquals(savedReservationId, actual.getId());
        assertEquals(updateReservationRequest.getReservationDateTime(), actual.getReservationDateTime());
        assertEquals(updateReservationRequest.getGuestNumber(), actual.getGuestNumber());
    }

    @Test
    void complete() throws Exception {
        // GIVEN
        Reservation savedReservation = reservationController.create(createReservationRequest);
        Long savedReservationId = savedReservation.getId();

        // WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/reservations/{id}/complete", savedReservationId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Reservation actual = MAPPER.readValue(result.getResponse().getContentAsString(), Reservation.class);

        // THEN
        assertEquals(COMPLETED, actual.getStatus());
        assertEquals(savedReservationId, actual.getId());
    }

    @Test
    void cancel() throws Exception {
        // GIVEN
        Reservation savedReservation = reservationController.create(createReservationRequest);
        Long savedReservationId = savedReservation.getId();

        // WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/reservations/{id}/cancel", savedReservationId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Reservation actual = MAPPER.readValue(result.getResponse().getContentAsString(), Reservation.class);

        // THEN
        assertEquals(CANCELLED, actual.getStatus());
        assertEquals(savedReservationId, actual.getId());
    }

    @Test
    void delete() throws Exception {
        // GIVEN
        Reservation savedReservation = reservationController.create(createReservationRequest);
        Long savedReservationId = savedReservation.getId();
        reservationController.cancel(savedReservationId);

        // WHEN
        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/reservations/{id}", savedReservationId)
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNoContent())
        .andReturn();

        // THEN
        assertThrows(ReservationNotFoundException.class, () -> reservationController.delete(savedReservationId));
    }

    @Test
    void getHistory() throws Exception {
        // GIVEN
        // allow non-parallel creation when running all tests together
        Thread.sleep(500);
        Reservation savedReservation = reservationController.create(createReservationRequest);
        Long savedReservationId = savedReservation.getId();
        reservationController.complete(savedReservationId);

        // WHEN
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/reservations/history")
                                .param("eateryId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List<ReservationHistory> actual = MAPPER.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        // THEN
        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(savedReservationId, actual.getFirst().getReservationId());
        assertEquals(CONFIRMED, actual.getFirst().getStatus());
        assertEquals(savedReservationId, actual.getLast().getReservationId());
        assertEquals(COMPLETED, actual.getLast().getStatus());
        assertTrue(actual.getFirst().getTimestamp().before(actual.getLast().getTimestamp()));
    }
}
