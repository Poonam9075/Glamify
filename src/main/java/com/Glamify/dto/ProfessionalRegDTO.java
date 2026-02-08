package com.Glamify.dto;
import com.Glamify.entities.Gender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfessionalRegDTO {

    // ---------- USER FIELDS ----------
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Gender gender;

    // ---------- PROFESSIONAL FIELDS ----------
    private String speciality;
    private int experienceInYears;
}
