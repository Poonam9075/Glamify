package com.Glamify.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.ServiceCreateDTO;
import com.Glamify.services.AdminService;
import com.Glamify.services.AdminServiceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/services")
@RequiredArgsConstructor
public class AdminServiceController {

    private final AdminServiceService service;
    private final AdminService adminService;

    //add service
    @PostMapping
    public ResponseEntity<ApiResponse> addService(
            @RequestBody ServiceCreateDTO dto) {

        return ResponseEntity.ok(service.addService(dto));
    }
    
    //update service
    @PutMapping("/{serviceId}")
    public ResponseEntity<ApiResponse> updateService(
            @PathVariable Long serviceId,
            @RequestBody ServiceCreateDTO dto) {

        return ResponseEntity.ok(
                adminService.updateService(serviceId, dto)
        );
    }

}
