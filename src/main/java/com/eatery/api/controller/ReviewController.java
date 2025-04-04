package com.eatery.api.controller;

import com.eatery.api.dto.CreateReviewRequest;
import com.eatery.api.dto.UpdateReviewRequest;
import com.eatery.entity.Review;
import com.eatery.exception.ReviewBadRequestException;
import com.eatery.exception.ReviewNotFoundException;
import com.eatery.service.ReviewService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling review-related operations, including
 * fetching all reviews, creating a new review, updating a review,
 * retrieving a review by ID, and deleting a review.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    /**
     * Fetches all reviews from the database.
     * @return A list of all reviews.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned"),
            @ApiResponse(responseCode = "404", description = "Reviews not found"),
            @ApiResponse(responseCode = "500", description = "Reviews could not be fetched")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Review> getAll() {
        return reviewService.getAll();
    }

    /**
     * Fetches all reviews by eatery id.
     * @return A list of all reviews with eatery id.
     */
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully returned")})
    @GetMapping(path = "/eatery/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Review> getAllByEateryId(@PathVariable Long id) {
        return reviewService.getAllByEateryId(id);
    }

    /**
     * Fetches all reviews by customer id.
     * @return A list of all reviews with customer id.
     */
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully returned")})
    @GetMapping(path = "/customer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Review> getAllByCustomerId(@PathVariable Long id) {
        return reviewService.getAllByCustomerId(id);
    }

    /**
     * Creates a new review based on the provided details.
     * @param newReview The review data to create a new review.
     * @return The created review.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Review could not be created")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Review create(@RequestBody CreateReviewRequest newReview) {
        return reviewService.create(newReview);
    }

    /**
     * Fetches a review by their ID.
     * @param id The ID of the review to retrieve.
     * @return The review with the specified ID.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned"),
            @ApiResponse(responseCode = "404", description = "Review not found"),
            @ApiResponse(responseCode = "500", description = "Review could not be fetched")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Review get(@PathVariable Long id) {
        return reviewService.get(id);
    }

    /**
     * Updates an existing review with the provided new review details.
     * @param newReview The new details for the review.
     * @param id The ID of the review to update.
     * @return The updated review.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully modified"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Review not found"),
            @ApiResponse(responseCode = "500", description = "Review could not be updated")
    })
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Review replace(@RequestBody UpdateReviewRequest newReview, @PathVariable Long id) {
        return reviewService.replace(newReview, id);
    }

    /**
     * Deletes the review with the specified ID.
     * @param id The ID of the review to delete.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Review not found"),
            @ApiResponse(responseCode = "500", description = "Review could not be deleted")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        reviewService.delete(id);
    }

    /**
     * Exception handlers for Review-related exceptions.
     * Below methods handle exceptions related to Review and sends the appropriate HTTP responses.
     */
    @ExceptionHandler(ReviewNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handle(ReviewNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ReviewBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handle(ReviewBadRequestException e) {
        return e.getMessage();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String handle(RuntimeException e) {
        return e.getMessage();
    }
}
