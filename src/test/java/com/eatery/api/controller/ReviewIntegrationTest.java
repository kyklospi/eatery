package com.eatery.api.controller;

import com.eatery.api.dto.CreateReviewRequest;
import com.eatery.api.dto.UpdateReviewRequest;
import com.eatery.entity.Review;
import com.eatery.exception.ReviewNotFoundException;
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

/**
 * Integration tests for the ReviewController class.
 * This class tests the controller's functionality with real database interactions.
 */
@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
public class ReviewIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ReviewController reviewController;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private CreateReviewRequest reviewRequest;

    /**
     * Set up method to initialize test data before each test.
     * Creates an instance of UpdateUserRequest for reuse in multiple tests.
     */
    @BeforeEach
    void setUp() {
        reviewRequest = new CreateReviewRequest(
                1,
                1,
                "I love it!",
                5
        );
    }

    @Test
    void create() throws Exception {
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/reviews")
                                .content(
                                        MAPPER.writeValueAsString(reviewRequest)
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        Review actual = MAPPER.readValue(result.getResponse().getContentAsString(), Review.class);

        assertNotNull(actual.getId());
        assertEquals(reviewRequest.getCustomerId(), actual.getCustomerId());
        assertEquals(reviewRequest.getEateryId(), actual.getEateryId());
        assertEquals(reviewRequest.getMessage(), actual.getMessage());
        assertEquals(reviewRequest.getRating(), actual.getRating());
    }

    @Test
    void getAll() throws Exception {
        reviewController.create(reviewRequest);

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/reviews")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List<Review> actual = MAPPER.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertNotNull(actual);
        assertNotNull(actual.getLast().getId());
        assertEquals(reviewRequest.getCustomerId(), actual.getLast().getCustomerId());
        assertEquals(reviewRequest.getEateryId(), actual.getLast().getEateryId());
        assertEquals(reviewRequest.getMessage(), actual.getLast().getMessage());
        assertEquals(reviewRequest.getRating(), actual.getLast().getRating());
    }

    @Test
    void getAllByEateryId() throws Exception {
        reviewController.create(reviewRequest);

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/reviews/eatery/{eateryId}", reviewRequest.getEateryId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List<Review> actual = MAPPER.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertNotNull(actual);
        assertNotNull(actual.getLast().getId());
        assertEquals(reviewRequest.getCustomerId(), actual.getLast().getCustomerId());
        assertEquals(reviewRequest.getEateryId(), actual.getLast().getEateryId());
        assertEquals(reviewRequest.getMessage(), actual.getLast().getMessage());
        assertEquals(reviewRequest.getRating(), actual.getLast().getRating());
    }

    @Test
    void getAllByCustomerId() throws Exception {
        reviewController.create(reviewRequest);

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/reviews/customer/{customerId}", reviewRequest.getCustomerId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        List<Review> actual = MAPPER.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertNotNull(actual);
        assertNotNull(actual.getLast().getId());
        assertEquals(reviewRequest.getCustomerId(), actual.getLast().getCustomerId());
        assertEquals(reviewRequest.getEateryId(), actual.getLast().getEateryId());
        assertEquals(reviewRequest.getMessage(), actual.getLast().getMessage());
        assertEquals(reviewRequest.getRating(), actual.getLast().getRating());
    }

    @Test
    void get() throws Exception {
        Review savedReview = reviewController.create(reviewRequest);
        Long savedReviewId = savedReview.getId();

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/reviews/{id}", savedReviewId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Review actual = MAPPER.readValue(result.getResponse().getContentAsString(), Review.class);

        assertEquals(savedReviewId, actual.getId());
        assertEquals(savedReview.getCustomerId(), actual.getCustomerId());
        assertEquals(savedReview.getEateryId(), actual.getEateryId());
        assertEquals(savedReview.getMessage(), actual.getMessage());
        assertEquals(savedReview.getRating(), actual.getRating());
    }

    @Test
    void replace() throws Exception {
        Review savedReview = reviewController.create(reviewRequest);
        Long savedReviewId = savedReview.getId();
        UpdateReviewRequest updateReviewRequest = new UpdateReviewRequest(
                "I love the place",
                4
        );

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/reviews/{id}", savedReviewId)
                                .content(
                                        MAPPER.writeValueAsString(updateReviewRequest)
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Review actual = MAPPER.readValue(result.getResponse().getContentAsString(), Review.class);

        assertEquals(updateReviewRequest.getMessage(), actual.getMessage());
        assertEquals(updateReviewRequest.getRating(), actual.getRating());
    }

    @Test
    void delete() throws Exception {
        Review savedReview = reviewController.create(reviewRequest);
        Long savedReviewId = savedReview.getId();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/reviews/{id}", savedReviewId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andReturn();

        assertThrows(ReviewNotFoundException.class, () -> reviewController.get(savedReviewId));
    }
}
