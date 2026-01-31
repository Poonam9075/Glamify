package com.glamify.service;

import java.util.List;
import com.glamify.dto.ProfessionalDto;

public interface ProfessionalService {

	ProfessionalDto findByEmail(String email);

    List<ProfessionalDto> searchBySpeciality(String speciality);
    
    List<ProfessionalDto> getAllProfessionals();

    ProfessionalDto updateProfile(Long id, ProfessionalDto updated);
}
