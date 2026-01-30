package com.glamify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PendingProfessionalResponse {

    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private String speciality;
    private int experienceInYears;
    private boolean approved;

}