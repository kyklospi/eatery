package com.eatery.repository;

import com.eatery.entity.Eatery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EateryRepository extends JpaRepository<Eatery, Long> {
}
