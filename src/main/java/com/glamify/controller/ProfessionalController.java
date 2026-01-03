package com.glamify.controller;

import com.glamify.entity.Appointment;
import com.glamify.entity.AppointmentStatus;
import com.glamify.repository.AppointmentRepository;
import com.glamify.service.AppointmentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professional")
public class ProfessionalController {

    private final AppointmentRepository appointmentRepo;
    private final AppointmentService appointmentService;

    public ProfessionalController(AppointmentRepository appointmentRepo,
                                  AppointmentService appointmentService) {
        this.appointmentRepo = appointmentRepo;
        this.appointmentService = appointmentService;
    }

    // =================================================
    // 1️⃣ VIEW MY ASSIGNED APPOINTMENTS
    // =================================================
    @GetMapping("/appointments")
    public List<Appointment> getMyAppointments() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        return appointmentRepo.findByProfessionalEmail(email);
    }

    // =================================================
    // 2️⃣ ACCEPT APPOINTMENT
    // =================================================
    @PutMapping("/appointment/{id}/accept")
    public Appointment accept(@PathVariable Long id) {
        return appointmentService.acceptAppointment(id);
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
