package com.Glamify.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.AppointmentCreateDTO;
import com.Glamify.dto.AppointmentResponseDTO;
import com.Glamify.services.AppointmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customer/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ApiResponse> bookAppointment(
            @RequestBody AppointmentCreateDTO request) {

        ApiResponse response = appointmentService.bookAppointment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> getMyAppointments() {

        return ResponseEntity.ok(
                appointmentService.getCustomerAppointments()
        );
    }
    
    @GetMapping("/professional")
    public ResponseEntity<List<AppointmentResponseDTO>> getProfessionalAppointments() {

        return ResponseEntity.ok(
                appointmentService.getProfessionalAppointments()
        );
    }

    @PutMapping("/professional/{appointmentId}/complete")
    public ResponseEntity<ApiResponse> completeAppointment(
            @PathVariable Long appointmentId) {

        return ResponseEntity.ok(
                appointmentService.markAppointmentCompleted(appointmentId)
        );
    }
    
    //Cancle appointment 
    @PutMapping("/{appointmentId}/cancel")
    public ResponseEntity<ApiResponse> cancelAppointment(
            @PathVariable Long appointmentId) {

        return ResponseEntity.ok(
                appointmentService.cancelAppointment(appointmentId)
        );
    }


}
