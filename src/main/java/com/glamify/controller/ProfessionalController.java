package com.glamify.controller;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.glamify.dto.AppointmentDto;
import com.glamify.dto.AppointmentViewDto;
import com.glamify.entity.AppointmentStatus;
import com.glamify.service.AppointmentService;

@RestController
@RequestMapping("/api/professional")
public class ProfessionalController {

    private final AppointmentService appointmentService;

    public ProfessionalController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    
    // Get professional assigned appointments
    @GetMapping("/appointments")
    public List<AppointmentViewDto> getProfessionalAppointments(Authentication authentication) {

    	return appointmentService.getAppointmentsByProfessional();
    }
    
    // Get available appointments
    @GetMapping("/available-appointments")
    public List<AppointmentViewDto> getAvailableAppointments() {
        return appointmentService.getUnacceptedAppointments();
    }
    
    // Accept appointment
    @PutMapping("/appointment/{id}/accept")
    public AppointmentDto acceptAppointment(@PathVariable Long id) {
        return appointmentService.acceptAppointmentByProfessional(id);
    }

    // Start appointment
    @PutMapping("/appointment/{id}/start")
    public AppointmentDto start(@PathVariable Long id) {
        return appointmentService.updateStatus(id, AppointmentStatus.IN_PROGRESS);
    }

    // Complete appointment
    @PutMapping("/appointment/{id}/complete")
    public AppointmentDto complete(@PathVariable Long id) {
        return appointmentService.updateStatus(id, AppointmentStatus.COMPLETED);
    }
}

