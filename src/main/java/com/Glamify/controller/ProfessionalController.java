package com.Glamify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.ProfessionalRegDTO;
import com.Glamify.services.ProfessionalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/professional")
@RequiredArgsConstructor
public class ProfessionalController {

    private final ProfessionalService professionalService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerProfessional(
            @RequestBody ProfessionalRegDTO request) {

        ApiResponse response =
                professionalService.registerProfessional(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    //------------- For Showing Customer with Service to Customer----------
    
//    @GetMapping("/by-service/{serviceId}")
//    public ResponseEntity<?> getProfessionalsByService(
//            @PathVariable Long serviceId) {
//
//        return ResponseEntity.ok(
//                professionalService.getProfessionalsByService(serviceId)
//        );
//    }

}
