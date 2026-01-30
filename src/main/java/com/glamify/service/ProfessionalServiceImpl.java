package com.glamify.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.glamify.entity.Professional;
import com.glamify.repository.ProfessionalRepository;

@Service
public class ProfessionalServiceImpl implements ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    public ProfessionalServiceImpl(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }


    @Override
    public Professional updateProfile(Long id, Professional updated) {
        Professional existing = professionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professional not found"));

        existing.setSpeciality(updated.getSpeciality());
        existing.setExperienceInYears(updated.getExperienceInYears());

        // rating NOT editable here
        return professionalRepository.save(existing);
    }

	@Override
	public Optional<Professional> findByEmail(String email) {
		return professionalRepository.findByEmail(email);
	}


	@Override
	public List<Professional> searchBySpeciality(String speciality) {
		return professionalRepository.findBySpeciality(speciality);
	}


	@Override
	public List<Professional> getAllProfessionals() {
		return professionalRepository.findAll();
	}


//	@Override
//	public List<Professional> findByIsActiveFalse() {
//		// TODO Auto-generated method stub
//		return professionalRepository.findByIsActiveFalse();
//	}

    
}
