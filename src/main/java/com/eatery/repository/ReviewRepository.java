package com.eatery.repository;

import com.eatery.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Review entities.
 * This interface extends JpaRepository, which provides CRUD operations
 * and additional functionality for working with Review entities.
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
