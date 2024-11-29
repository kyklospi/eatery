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

@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
class EateryManagerControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private EateryManagerController eateryManagerController;

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

        List<EateryManager> actual = MAPPER.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        // THEN
        assertNotNull(actual);
        assertNotNull(actual.getLast().getId());
        assertEquals(managerRequest.getFirstName(), actual.getLast().getFirstName());
        assertEquals(managerRequest.getLastName(), actual.getLast().getLastName());
        assertEquals(managerRequest.getEmail(), actual.getLast().getEmail());
        assertEquals(managerRequest.getPhoneNumber(), actual.getLast().getPhoneNumber());
    }

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