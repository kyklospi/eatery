package com.eatery.repository;

import com.eatery.entity.Eatery;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Eatery entities.
 * This interface extends JpaRepository, which provides CRUD operations
 * and additional functionality for working with Eatery entities.
 */
public interface EateryRepository extends JpaRepository<Eatery, Long> {
}
