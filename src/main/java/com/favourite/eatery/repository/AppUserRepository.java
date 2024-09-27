package com.favourite.eatery.repository;

import com.favourite.eatery.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
}
