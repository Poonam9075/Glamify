package com.glamify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.glamify.dto.CustomerRegisterRequest;
import com.glamify.dto.UserLoginRequest;
import com.glamify.dto.UserLoginResponse;
import com.glamify.dto.ProfessionalRegisterRequest;
import com.glamify.dto.UserRegisterResponse;
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
    public ResponseEntity<UserLoginResponse> login(
            @Valid @RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    
    @PostMapping("/register/customer")
    public ResponseEntity<UserRegisterResponse> registerCustomer(
            @Valid @RequestBody CustomerRegisterRequest request) {

        UserRegisterResponse response = authService.registerCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/register/professional")
    public ResponseEntity<UserRegisterResponse> registerProfessional(
            @Valid @RequestBody ProfessionalRegisterRequest request) {

        UserRegisterResponse response = authService.registerProfessional(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
