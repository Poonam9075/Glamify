package com.glamify.controller;

import com.glamify.entity.Appointment;
import com.glamify.entity.AppointmentStatus;
import com.glamify.entity.User;
import com.glamify.repository.AppointmentRepository;
import com.glamify.repository.UserRepository;
import com.glamify.service.AppointmentService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professional")
public class ProfessionalController {

    private final AppointmentRepository appointmentRepo;
    private final UserRepository userRepo;
    private final AppointmentService appointmentService;

    public ProfessionalController(AppointmentRepository appointmentRepo,
    							  UserRepository userRepo,
                                  AppointmentService appointmentService) {
        this.appointmentRepo = appointmentRepo;
		this.userRepo = userRepo;
        this.appointmentService = appointmentService;
    }

    // =================================================
    // 1️⃣ VIEW MY ASSIGNED APPOINTMENTS
    // =================================================
//    @GetMapping("/my-appointments")
//    public List<Appointment> getMyAppointments() {
//        String email = SecurityContextHolder.getContext()
//                .getAuthentication().getName();
//
//        return appointmentRepo.findByProfessional_User_Email(email);
//    }
    
    @GetMapping("/my-appointments")
    public List<Appointment> getMyAppointments(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow();

        return appointmentRepo.findByProfessionalUserId(user.getUserId());
    }

    
    @GetMapping("/available-appointments")
    public List<Appointment> getAvailableAppointments() {
        return appointmentRepo.findByProfessionalIsNullAndStatus(
                AppointmentStatus.CREATED
        );
    }
    
    // =================================================
    // 2️⃣ ACCEPT APPOINTMENT
    // =================================================
    @PutMapping("/appointment/{id}/accept")
    public Appointment acceptAppointment(@PathVariable Long id) {
        return appointmentService.acceptAppointmentByProfessional(id);
    }

    // =================================================
    // 3️⃣ START APPOINTMENT
    // =================================================
    @PutMapping("/appointment/{id}/start")
    public Appointment start(@PathVariable Long id) {
        return appointmentService.startAppointment(id);
    }

    // =================================================
    // 4️⃣ COMPLETE APPOINTMENT
    // =================================================
    @PutMapping("/appointment/{id}/complete")
    public Appointment complete(@PathVariable Long id) {
        return appointmentService.completeAppointment(id);
    }
}

