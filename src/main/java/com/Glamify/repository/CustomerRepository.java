package com.Glamify.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Glamify.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByUserId(Long userId);
    // No extra code needed
}
