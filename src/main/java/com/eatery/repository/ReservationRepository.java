package com.eatery.repository;

import com.eatery.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Reservation entities.
 * This interface extends JpaRepository, which provides CRUD operations
 * and additional functionality for working with Reservation entities.
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
