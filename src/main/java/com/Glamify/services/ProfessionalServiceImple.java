package com.Glamify.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.ProfessionalApprovalDTO;
import com.Glamify.dto.ProfessionalRegDTO;
import com.Glamify.entities.Professional;
import com.Glamify.entities.ProfessionalStatus;
import com.Glamify.entities.Status;
import com.Glamify.entities.User;
import com.Glamify.entities.UserRole;
import com.Glamify.exceptions.ApiException;
import com.Glamify.exceptions.DuplicateResourceException;
import com.Glamify.exceptions.ResourceNotFoundException;
import com.Glamify.repository.ProfessionalRepository;
import com.Glamify.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfessionalServiceImple implements ProfessionalService {

    private final UserRepository userRepository;
    private final ProfessionalRepository professionalRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse registerProfessional(ProfessionalRegDTO request) {

        // ✅ 1. Duplicate Email Check
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already registered: " + request.getEmail());
        }

        // ✅ 2. Duplicate Phone Check
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException(
                    "Phone already registered: " + request.getPhone());
        }

        // ✅ 3. Create User
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setGender(request.getGender());

        // encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // assign role & status
        user.setRole(UserRole.ROLE_PROFESSIONAL);
        user.setIsActive(Status.ACTIVE);

        User savedUser = userRepository.save(user);

        // ✅ 4. Create Professional
        Professional professional = new Professional();
        professional.setSpeciality(request.getSpeciality());
        professional.setExperienceInYears(request.getExperienceInYears());

        // default status = PENDING
        professional.setStatus(ProfessionalStatus.PENDING);

        // map user
        professional.setUser(savedUser);

        // save professional
        professionalRepository.save(professional);

        return new ApiResponse(
                "SUCCESS",
                "Professional registered successfully. Waiting for admin approval."
        );
    }
    
    @Override
    public ApiResponse updateProfessionalStatus(
            Long professionalId,
            ProfessionalApprovalDTO request) {

        // 1️⃣ Fetch professional
        Professional professional = professionalRepository
                .findById(professionalId)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Professional not found with id: " + professionalId));

        // 2️⃣ Validate current status
        if (professional.getStatus() != ProfessionalStatus.PENDING) {
            throw new ApiException(
                "Professional already " + professional.getStatus());
        }

        // 3️⃣ Update status (DTO → Entity)
        professional.setStatus(request.getStatus());

        // 4️⃣ Save
        professionalRepository.save(professional);

        return new ApiResponse(
                "SUCCESS",
                "Professional status updated to " + request.getStatus()
        );
    }

}
