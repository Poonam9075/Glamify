package com.Glamify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Glamify.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // No extra code needed
}
