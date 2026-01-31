package com.glamify.dto;

import com.glamify.entity.Gender;
import com.glamify.entity.Role;

import lombok.Data;

@Data
public class UserDto {

	private Long userId;
    protected String fullName;
    private String email;
    private String password;
    protected String phone;
    protected Gender gender;
    private Role userRole;
    private boolean active;
	
}
