package com.Glamify.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.ProfessionalApprovalDTO;
import com.Glamify.services.ProfessionalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProfessionalService professionalService;

    @PutMapping("/professionals/{id}/status")
    public ResponseEntity<ApiResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody ProfessionalApprovalDTO request) {

        return ResponseEntity.ok(
                professionalService.updateProfessionalStatus(id, request)
        );
    }
}
