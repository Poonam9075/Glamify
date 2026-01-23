package com.Glamify.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Glamify.entities.Professional;

public interface ProfessionalRepository 
        extends JpaRepository<Professional, Long> {
	 Optional<Professional> findByUserId(Long userId);
	
}
