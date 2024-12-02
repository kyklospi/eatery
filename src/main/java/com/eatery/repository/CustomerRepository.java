package com.eatery.repository;

import com.eatery.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Customer entities.
 * This interface extends JpaRepository, which provides CRUD operations
 * and additional functionality for working with Customer entities.
 */
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
