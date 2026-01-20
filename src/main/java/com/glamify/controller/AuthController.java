package com.glamify.controller;

import com.glamify.dto.LoginRequest;
import com.glamify.dto.LoginResponse;
import com.glamify.entity.Customer;
import com.glamify.entity.Professional;
import com.glamify.entity.Role;
import com.glamify.entity.User;
import com.glamify.repository.CustomerRepository;
import com.glamify.repository.ProfessionalRepository;
import com.glamify.repository.UserRepository;
import com.glamify.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final CustomerRepository customerRepo;
    private final ProfessionalRepository professionalRepo;
    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;

    public AuthController(CustomerRepository customerRepo,
                          ProfessionalRepository professionalRepo,
                          UserRepository userRepo,
                          JwtUtil jwtUtil) {
        this.customerRepo = customerRepo;
        this.professionalRepo = professionalRepo;
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }

    
    // =================================================
    // REGISTER CUSTOMER
    // =================================================
    @PostMapping("/register/customer")
    public ResponseEntity<?> registerCustomer(
            @RequestBody Customer customer) {

        customer.setPassword(customer.getPassword()); // TO-DO need to add encryption * * * * * * * * * * * *

        customer.setUserRole(Role.CUSTOMER);

        Customer saved =
        		customerRepo.save(customer);

        return ResponseEntity.ok(saved);
    }


    // =================================================
    // REGISTER PROFESSIONAL
    // =================================================
    @PostMapping("/register/professional")
	public ResponseEntity<?> registerProfessional(
	        @RequestBody Professional professional) {
	
	    professional.setPassword(
	        professional.getPassword() // TO-DO need to add encryption * * * * * * * * * * * *
	    );
		
	    professional.setUserRole(Role.PROFESSIONAL);
	
	    Professional saved =
	    		professionalRepo.save(professional);
	
	    return ResponseEntity.ok(saved);
	}

/*
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        // 1️⃣ Validate input
        if (request.getFullName() == null || request.getFullName().isBlank() ||
            request.getEmail() == null || request.getEmail().isBlank() ||
    		request.getPhone() == null || request.getPhone().isBlank() ||
            request.getPassword() == null || request.getPassword().isBlank() ||
            request.getRole() == null) {

            return ResponseEntity.badRequest()
                    .body(Map.of("error", "All fields are required"));
        }

        // 2️⃣ Prevent ADMIN registration
        if ("ADMIN".equalsIgnoreCase(request.getRole())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid role"));
        }

        // 3️⃣ Check duplicate email
        if (userRepo.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Email already registered"));
        }
        
        if (request.getPhone() == null ||
    	    !request.getPhone().matches("^[6-9]\\d{9}$")) {

    	    return ResponseEntity.badRequest()
    	        .body(Map.of("error", "Invalid phone number"));
    	}

    	if (userRepo.existsByPhone(request.getPhone())) {
    	    return ResponseEntity.badRequest()
    	        .body(Map.of("error", "Phone number already registered"));
    	}


        // 4️⃣ Create user
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        user.setUserRole(request.getRole());
        user.setActive(true); // ✅ enabled by default

        userRepo.save(user);

        return ResponseEntity.ok(Map.of("message", "Registration successful"));
    }
*/
    
    // =================================================
    // LOGIN (DB VALIDATED + JWT)
    // =================================================
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        String email = request.getUsername();
        String password = request.getPassword();

        // 1️⃣ Fetch user from DB
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // 2️⃣ Validate password (plain for now)
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }
        
        if (!user.isActive()) {
            throw new DisabledException("User account is disabled");
        }

        // 3️⃣ Get role from DB (NOT frontend)
        String role = user.getUserRole().name();
        String fullName = user.getFullName();

        // 4️⃣ Generate token with DB role
        String token = jwtUtil.generateToken(email, role);

        return new LoginResponse(token, role, fullName);
    }


}
