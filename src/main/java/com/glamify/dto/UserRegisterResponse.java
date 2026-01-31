package com.glamify.dto;

import lombok.Data;

@Data
public class UserRegisterResponse {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private String role;

    // getters & setters
}
