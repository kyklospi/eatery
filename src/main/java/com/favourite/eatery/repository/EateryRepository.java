package com.favourite.eatery.repository;

import com.favourite.eatery.model.Eatery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EateryRepository extends JpaRepository<Eatery, Long> {
}
