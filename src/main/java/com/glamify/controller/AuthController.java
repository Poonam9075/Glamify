package com.glamify.controller;

import com.glamify.entity.Customer;
import com.glamify.entity.Professional;
import com.glamify.entity.User;
import com.glamify.repository.CustomerRepository;
import com.glamify.repository.ProfessionalRepository;
import com.glamify.repository.UserRepository;
import com.glamify.security.JwtUtil;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
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
    public Customer registerCustomer(@RequestBody Customer customer) {
        customer.setUserRole("CUSTOMER");
        return customerRepo.save(customer);
    }

    // =================================================
    // REGISTER PROFESSIONAL
    // =================================================
    @PostMapping("/register/professional")
    public Professional registerProfessional(@RequestBody Professional professional) {
        professional.setUserRole("PROFESSIONAL");
        professional.setRating(0.0);
        return professionalRepo.save(professional);
    }

    // =================================================
    // LOGIN (DB VALIDATED + JWT)
    // =================================================
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        // 1️⃣ Fetch user from DB
        User user = userRepo.findByEmail(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2️⃣ Password validation (plain text for now)
        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // 3️⃣ Role validation
        if (!user.getUserRole().equals(request.getRole())) {
            throw new RuntimeException("Invalid role");
        }

        // 4️⃣ Generate JWT
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getUserRole()
        );

        return new LoginResponse(token, user.getUserRole());
    }
}
