package com.eatery.repository;

import com.eatery.entity.EateryManager;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing EateryManager entities.
 * This interface extends JpaRepository, which provides CRUD operations
 * and additional functionality for working with EateryManager entities.
 */
public interface EateryManagerRepository extends JpaRepository<EateryManager, Long> {
}
