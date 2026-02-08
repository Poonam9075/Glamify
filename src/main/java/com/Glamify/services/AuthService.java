package com.Glamify.services;

import com.Glamify.dto.AuthRequest;
import com.Glamify.dto.AuthResponse;

public interface AuthService {

    AuthResponse login(AuthRequest request);
}
