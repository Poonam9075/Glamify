package com.Glamify.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Glamify.entities.Professional;
import com.Glamify.entities.ProfessionalStatus;

public interface ProfessionalRepository 
        extends JpaRepository<Professional, Long> {
	 Optional<Professional> findByUserId(Long userId);
	
	 @Query("""
			 SELECT p FROM Professional p
			 WHERE p.status = :status
			 AND p.speciality = :speciality
			 """)
			 List<Professional> findApprovedProfessionals(
			         ProfessionalStatus status,
			         String speciality);

}
