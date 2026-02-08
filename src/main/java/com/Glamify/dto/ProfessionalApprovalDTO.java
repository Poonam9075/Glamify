package com.Glamify.dto;

import com.Glamify.entities.ProfessionalStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessionalApprovalDTO {

    private ProfessionalStatus status; // APPROVED or REJECTED
}
