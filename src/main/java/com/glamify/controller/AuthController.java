package com.glamify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.glamify.dto.CustomerRegisterRequest;
import com.glamify.dto.LoginRequest;
import com.glamify.dto.LoginResponse;
import com.glamify.dto.ProfessionalRegisterRequest;
import com.glamify.dto.RegisterResponse;
import com.glamify.security.JwtUtil;
import com.glamify.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

	private final AuthService authService;
	
    public AuthController(AuthService authService,
                          JwtUtil jwtUtil) 
    {
        this.authService = authService;
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    
    @PostMapping("/register/customer")
    public ResponseEntity<RegisterResponse> registerCustomer(
            @Valid @RequestBody CustomerRegisterRequest request) {

        RegisterResponse response = authService.registerCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/register/professional")
    public ResponseEntity<RegisterResponse> registerProfessional(
            @Valid @RequestBody ProfessionalRegisterRequest request) {

        RegisterResponse response = authService.registerProfessional(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
