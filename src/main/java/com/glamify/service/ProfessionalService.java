package com.glamify.service;

import java.util.List;
import java.util.Optional;

import com.glamify.entity.Professional;

public interface ProfessionalService {

	Optional<Professional> findByEmail(String email);

    List<Professional> searchBySpeciality(String speciality);
    
    List<Professional> getAllProfessionals();

    Professional updateProfile(Long id, Professional updated);
}
