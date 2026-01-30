package com.glamify.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.glamify.entity.Professional;

public interface ProfessionalRepository
        extends JpaRepository<Professional, Long> {

    Optional<Professional> findByEmail(String email);
    
    List<Professional> findBySpeciality(String speciality);
    
    List<Professional> findByApprovedFalse();
    
    List<Professional> findByApprovedTrue();
    
}
