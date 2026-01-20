package com.glamify.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username; // email
    private String password;
    private String role;     // ADMIN / CUSTOMER / PROFESSIONAL
}
