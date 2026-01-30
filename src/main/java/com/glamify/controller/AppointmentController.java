package com.glamify.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.glamify.entity.Appointment;
import com.glamify.entity.AppointmentStatus;
import com.glamify.service.AppointmentServiceImpl;

@RestController
//@RequestMapping("/api/appointment")
public class AppointmentController {

	private final AppointmentServiceImpl appointmentService;

	public AppointmentController(AppointmentServiceImpl appointmentService) {
		this.appointmentService = appointmentService;
	}

	// Get appointment by appointmentId
	@GetMapping("/appointment/{appointmentId}")
    public Appointment getAppointmentDetails(@PathVariable Long appointmentId) {
        return appointmentService.getAppointmentDetails(appointmentId);
    }
	
	// Update appointment
	@PutMapping("/appointment/{appointmentId}")
	public ResponseEntity<Appointment> updateStatus(@PathVariable Long appointmentId,
													@RequestParam AppointmentStatus status) {
		return ResponseEntity.ok().body(appointmentService.updateStatus(appointmentId, status));
	}
}