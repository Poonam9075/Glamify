package com.glamify.controller;

import com.glamify.entity.*;
import com.glamify.repository.AppointmentRepository;
import com.glamify.service.AppointmentService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final AppointmentService appointmentService;
    private final AppointmentRepository appointmentRepo;

    public CustomerController(AppointmentService appointmentService,
                              AppointmentRepository appointmentRepo) {
        this.appointmentService = appointmentService;
        this.appointmentRepo = appointmentRepo;
    }

    // =================================================
    // 1️⃣ CREATE APPOINTMENT (CUSTOMER FROM JWT)
    // =================================================
    @PostMapping("/appointment")
    public Appointment createAppointment(
            @RequestBody AppointmentRequest request) {

        return appointmentService.createAppointment(
                request.getAppointment(),
                request.getBookedServices()
        );
    }

    // =================================================
    // 2️⃣ VIEW ALL MY APPOINTMENTS
    // =================================================
    @GetMapping("/appointments/{customerId}")
    public List<Appointment> getMyAppointments(
            @PathVariable Long customerId) {

        return appointmentRepo.findByCustomerUserId(customerId);
    }

    // =================================================
    // 3️⃣ CANCEL APPOINTMENT
    // CREATED / ACCEPTED → CANCELLED
    // =================================================
    @PutMapping("/appointment/{appointmentId}/cancel")
    public Appointment cancelAppointment(
            @PathVariable Long appointmentId) {

        return appointmentService.updateStatus(
                appointmentId,
                AppointmentStatus.CANCELLED
        );
    }

    // =================================================
    // 4️⃣ VIEW SINGLE APPOINTMENT DETAILS
    // =================================================
    @GetMapping("/appointment/{appointmentId}")
    public Appointment getAppointmentDetails(
            @PathVariable Long appointmentId) {

        return appointmentRepo.findById(appointmentId)
                .orElseThrow(() ->
                        new RuntimeException("Appointment not found"));
    }

    // =================================================
    // 5️⃣ PAY FOR COMPLETED APPOINTMENT
    // =================================================
    @PostMapping("/payment/{invoiceId}")
    public Payment makePayment(
            @PathVariable Long invoiceId,
            @RequestParam String method) {

        return appointmentService.makePayment(invoiceId, method);
    }
}
