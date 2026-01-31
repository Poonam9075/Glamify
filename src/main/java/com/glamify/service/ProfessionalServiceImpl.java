package com.glamify.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.glamify.dto.ProfessionalDto;
import com.glamify.dto.mapper.ProfessionalMapper;
import com.glamify.entity.Professional;
import com.glamify.repository.ProfessionalRepository;

@Service
public class ProfessionalServiceImpl implements ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    public ProfessionalServiceImpl(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }


    @Override
    public ProfessionalDto updateProfile(Long id, ProfessionalDto updated) {
        Professional existing = professionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professional not found"));

        existing.setSpeciality(updated.getSpeciality());
        existing.setExperienceInYears(updated.getExperienceInYears());

        // rating NOT editable here
        return ProfessionalMapper.toDto(professionalRepository.save(existing));
    }

	@Override
	public ProfessionalDto findByEmail(String email) {
		
		Optional<Professional> optProfessional = professionalRepository.findByEmail(email);
		Professional professional = optProfessional.orElseThrow();
		
		return ProfessionalMapper.toDto(professional);
	}


	@Override
	public List<ProfessionalDto> searchBySpeciality(String speciality) {
		return ProfessionalMapper.toDtolist(professionalRepository.findBySpeciality(speciality));
	}


	@Override
	public List<ProfessionalDto> getAllProfessionals() {
		return ProfessionalMapper.toDtolist(professionalRepository.findAll());
	}


//	@Override
//	public List<Professional> findByIsActiveFalse() {
//		// TODO Auto-generated method stub
//		return professionalRepository.findByIsActiveFalse();
//	}

    
}
