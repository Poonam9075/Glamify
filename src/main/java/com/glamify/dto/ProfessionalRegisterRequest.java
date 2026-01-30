package com.glamify.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ProfessionalRegisterRequest extends UserRegisterRequest {

    @Min(0)
    private int experience;
    
    private String speciality;
    
}