package com.glamify.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.glamify.dto.CustomerRegisterRequest;
import com.glamify.dto.LoginRequest;
import com.glamify.dto.LoginResponse;
import com.glamify.dto.ProfessionalRegisterRequest;
import com.glamify.dto.RegisterResponse;
import com.glamify.entity.Customer;
import com.glamify.entity.Gender;
import com.glamify.entity.Professional;
import com.glamify.entity.Role;
import com.glamify.entity.User;
import com.glamify.repository.UserRepository;
import com.glamify.security.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

	
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ---------------- CUSTOMER REGISTRATION ----------------

    @Override
    public RegisterResponse registerCustomer(CustomerRegisterRequest request) {

        Customer customer = new Customer();
        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail().trim().toLowerCase());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setPhone(request.getPhone());
        customer.setGender(parseGender(request.getGender()));
        customer.setRole(Role.CUSTOMER);
        customer.setActive(true);

        Customer saved = userRepository.save(customer);

        return mapToResponse(saved);
    }

    // ---------------- PROFESSIONAL REGISTRATION ----------------

    @Override
    public RegisterResponse registerProfessional(ProfessionalRegisterRequest request) {

        Professional professional = new Professional();
        professional.setFullName(request.getFullName());
        professional.setEmail(request.getEmail().trim().toLowerCase());
        professional.setPassword(passwordEncoder.encode(request.getPassword()));
        professional.setPhone(request.getPhone());
        professional.setGender(parseGender(request.getGender()));
        professional.setExperienceInYears(request.getExperience());
        professional.setRole(Role.PROFESSIONAL);
        professional.setRating(0.0);
        professional.setSpeciality(request.getSpeciality());
        professional.setActive(true);
        professional.setApproved(false);

        Professional saved = userRepository.save(professional);

        return mapToResponse(saved);
    }

    // ---------------- HELPERS ----------------

    private Gender parseGender(String gender) {
        if (gender == null) return null;
        return Gender.valueOf(gender.toUpperCase());
    }

    private RegisterResponse mapToResponse(User user) {
        RegisterResponse response = new RegisterResponse();
        response.setId(user.getUserId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setGender(user.getGender() != null ? user.getGender().name() : null);
        response.setRole(user.getRole().name());
        return response;
    }
    
    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        
        if (!user.isActive()) {
            throw new RuntimeException("User account is inactive");
        }
        
        if (user.getRole() == Role.PROFESSIONAL) {
        	Professional p = (Professional) user;
        	if (!p.isApproved()) {
        	throw new RuntimeException("Professional account is pending admin approval");
        	}
    	}

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setRole(user.getRole().name());
        
        return response;
    }

}
