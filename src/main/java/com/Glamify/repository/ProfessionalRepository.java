package com.Glamify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Glamify.entities.Professional;

public interface ProfessionalRepository 
        extends JpaRepository<Professional, Long> {
}
