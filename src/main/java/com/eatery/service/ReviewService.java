package com.eatery.service;

import com.eatery.api.dto.CreateReviewRequest;
import com.eatery.api.dto.UpdateReviewRequest;
import com.eatery.entity.Review;
import com.eatery.exception.ReviewBadRequestException;
import com.eatery.exception.ReviewNotFoundException;
import com.eatery.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling business logic related to reviews.
 * This class interacts with the repository to perform CRUD operations on review data.
 */
@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    /**
     * Retrieves all reviews from the database.
     * @return A list of all reviews.
     */
    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    /**
     * Retrieves all reviews with eatery id from the database.
     * @param id eatery id
     * @return A list of all reviews with eatery id.
     */
    public List<Review> getAllByEateryId(Long id) {
        return reviewRepository.findAll()
                .stream()
                .filter(review -> review.getEateryId() == id)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all reviews with customer id from the database.
     * @param id customer id
     * @return A list of all reviews with customer id.
     */
    public List<Review> getAllByCustomerId(Long id) {
        return reviewRepository.findAll()
                .stream()
                .filter(review -> review.getCustomerId() == id)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new review after validating the provided data.
     * @param newReviewRequest The review object to be created.
     * @return The created review object.
     * @throws ReviewBadRequestException if the provided review data is invalid.
     */
    public Review create(CreateReviewRequest newReviewRequest) {
        validateReview(newReviewRequest);
        Review review = new Review(
                newReviewRequest.getEateryId(),
                newReviewRequest.getCustomerId(),
                newReviewRequest.getMessage(),
                newReviewRequest.getRating()
        );
        return reviewRepository.save(review);
    }

    /**
     * Deletes a review by their ID.
     * @param id The ID of the review to be deleted.
     * @throws ReviewNotFoundException if the review with the specified ID does not exist.
     */
    public void delete(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(ReviewNotFoundException::new);
        reviewRepository.delete(review);
    }

    /**
     * Retrieves a review by their ID.
     * @param id The ID of the review to retrieve.
     * @return The review object with the specified ID.
     * @throws ReviewNotFoundException if the review with the specified ID does not exist.
     */
    public Review get(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(ReviewNotFoundException::new);
    }

    /**
     * Validates the review data to ensure required fields are not null or empty.
     * @param newReview The review object to be validated.
     * @throws ReviewBadRequestException if any required field is missing or empty.
     */
    public Review replace(UpdateReviewRequest newReview, Long id) {
        return reviewRepository.findById(id)
                .map(review -> {
                    review.setMessage(newReview.getMessage());
                    review.setRating(newReview.getRating());
                    return reviewRepository.save(review);
                })
                .orElseThrow(ReviewNotFoundException::new);
    }

    private void validateReview(CreateReviewRequest review) {
        if (review == null) {
            throw new ReviewBadRequestException("Review request must not be null.");
        }
        if (review.getCustomerId() == 0) {
            throw new ReviewBadRequestException("Customer id must not be 0.");
        }
        if (review.getEateryId() == 0) {
            throw new ReviewBadRequestException("Eatery id must not be 0.");
        }
        if (review.getMessage() == null || review.getMessage().isBlank()) {
            throw new ReviewBadRequestException("Message must not be null or empty.");
        }
        if (review.getRating() == 0) {
            throw new ReviewBadRequestException("Rating must be greater than 0.");
        }
    }
}
