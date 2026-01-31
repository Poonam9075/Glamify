package com.glamify.dto;

import com.glamify.entity.Gender;
import com.glamify.entity.Role;
import lombok.Data;

@Data
public class ProfessionalDto {

	private Long userId;
    protected String fullName;
    private String email;
    private String password;
    protected String phone;
    protected Gender gender;
    private Role userRole;
    private boolean active;

    private String speciality;
    private int experienceInYears;
    private double rating;
    private boolean approved;
}
