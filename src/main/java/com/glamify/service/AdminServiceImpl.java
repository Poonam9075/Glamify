package com.glamify.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.glamify.dto.PendingProfessionalResponse;
import com.glamify.dto.UserDto;
import com.glamify.dto.mapper.UserMapper;
import com.glamify.entity.Professional;
import com.glamify.entity.User;
import com.glamify.repository.ProfessionalRepository;
import com.glamify.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService {

    private final ProfessionalRepository professionalRepository;
    private final UserRepository userRepository;

    public AdminServiceImpl(
				            ProfessionalRepository professionalRepository,
				            UserRepository userRepository
				    		) {
        this.professionalRepository = professionalRepository;
        this.userRepository = userRepository;
    }
   
    @Override
    public void approveProfessional(Long professionalId) {
        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() ->
                        new RuntimeException("Professional not found")
                );

        professional.setApproved(true);
        professionalRepository.save(professional);
    }

    @Override
	public List<PendingProfessionalResponse> getPendingProfessionals() {
    	return professionalRepository.findByApprovedFalse()
    			.stream()
    			.map(p -> new PendingProfessionalResponse(
	    			p.getUserId(),
	    			p.getFullName(),
	    			p.getEmail(),
	    			p.getPhone(),
	    			p.getGender().name(),
	    			p.getSpeciality(),
	    			p.getExperienceInYears(),
	    			p.isApproved()
    			))
    			.toList();
	}
    
    @Override
    public void deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return UserMapper.toDtolist(userRepository.findAll());
    }
	
    @Override
    public String updateUser(
            @PathVariable Long id,
            @RequestBody UserDto updatedUserDto) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Prevent admin from disabling himself
        String currentEmail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        if (existingUser.getEmail().equals(currentEmail)
                && !updatedUserDto.isActive()) {
            return "Admin cannot disable own account";
        }

        // Update editable fields
        existingUser.setFullName(updatedUserDto.getFullName());
        existingUser.setEmail(updatedUserDto.getEmail());
        existingUser.setRole(updatedUserDto.getUserRole());
        existingUser.setActive(updatedUserDto.isActive());

        // Update password ONLY if provided
        if (updatedUserDto.getPassword() != null
                && !updatedUserDto.getPassword().isBlank()) {
            existingUser.setPassword(updatedUserDto.getPassword());
        }

        userRepository.save(existingUser);

        return "User upated successfully";
    }
    
    @Override
    public User toggleUser(@PathVariable Long id) {

        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getEmail().equals(SecurityContextHolder
                .getContext().getAuthentication().getName())) {
            throw new RuntimeException("Admin cannot disable own account");
        }
        
        user.setActive(!user.isActive());
        userRepository.save(user);

        return user;
    }
    
}
