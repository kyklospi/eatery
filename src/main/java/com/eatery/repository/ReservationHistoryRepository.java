package com.eatery.repository;

import com.eatery.entity.ReservationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationHistoryRepository extends JpaRepository<ReservationHistory,Long> {
}
