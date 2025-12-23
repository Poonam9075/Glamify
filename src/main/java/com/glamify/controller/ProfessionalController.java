package com.glamify.controller;

import com.glamify.entity.Appointment;
import com.glamify.entity.AppointmentStatus;
import com.glamify.repository.AppointmentRepository;
import com.glamify.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professional")
public class ProfessionalController {

    private final AppointmentService appointmentService;
    private final AppointmentRepository appointmentRepo;

    public ProfessionalController(AppointmentService appointmentService,
                                  AppointmentRepository appointmentRepo) {
        this.appointmentService = appointmentService;
        this.appointmentRepo = appointmentRepo;
    }

    // ================= VIEW ASSIGNED APPOINTMENTS =================
    @GetMapping("/appointments/{professionalId}")
    public List<Appointment> getAppointments(@PathVariable Long professionalId) {
        return appointmentRepo.findByProfessionalUserId(professionalId);
    }

    // ================= ACCEPT =================
    @PutMapping("/appointment/{id}/accept")
    public Appointment accept(@PathVariable Long id) {
        return appointmentService.updateStatus(id, AppointmentStatus.ACCEPTED);
    }

    // ================= START =================
    @PutMapping("/appointment/{id}/start")
    public Appointment start(@PathVariable Long id) {
        return appointmentService.updateStatus(id, AppointmentStatus.IN_PROGRESS);
    }

    // ================= COMPLETE =================
    @PutMapping("/appointment/{id}/complete")
    public Appointment complete(@PathVariable Long id) {
        return appointmentService.updateStatus(id, AppointmentStatus.COMPLETED);
    }

    // ================= CANCEL =================
    @PutMapping("/appointment/{id}/cancel")
    public Appointment cancel(@PathVariable Long id) {
        return appointmentService.updateStatus(id, AppointmentStatus.CANCELLED);
    }
}
