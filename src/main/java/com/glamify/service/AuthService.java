package com.glamify.service;

import com.glamify.dto.CustomerRegisterRequest;
import com.glamify.dto.UserLoginRequest;
import com.glamify.dto.UserLoginResponse;
import com.glamify.dto.ProfessionalRegisterRequest;
import com.glamify.dto.UserRegisterResponse;

public interface AuthService {

    UserRegisterResponse registerCustomer(CustomerRegisterRequest request);

    UserRegisterResponse registerProfessional(ProfessionalRegisterRequest request);
    
    UserLoginResponse login(UserLoginRequest request);
}
