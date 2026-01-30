package com.glamify.service;

import com.glamify.dto.CustomerRegisterRequest;
import com.glamify.dto.LoginRequest;
import com.glamify.dto.LoginResponse;
import com.glamify.dto.ProfessionalRegisterRequest;
import com.glamify.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse registerCustomer(CustomerRegisterRequest request);

    RegisterResponse registerProfessional(ProfessionalRegisterRequest request);
    
    LoginResponse login(LoginRequest request);
}
