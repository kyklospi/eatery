package com.favourite.eatery.repository;

import com.favourite.eatery.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Administrator, Long> {
}
