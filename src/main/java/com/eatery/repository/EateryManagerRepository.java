package com.eatery.repository;

import com.eatery.entity.EateryManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EateryManagerRepository extends JpaRepository<EateryManager, Long> {
}
