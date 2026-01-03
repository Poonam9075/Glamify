package com.glamify.controller;

import com.glamify.dto.CustomerBookingRequest;
import com.glamify.entity.*;
import com.glamify.repository.AppointmentRepository;
import com.glamify.repository.BeautyServiceRepository;
import com.glamify.service.AppointmentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final AppointmentService appointmentService;
    private final AppointmentRepository appointmentRepo;
    private final BeautyServiceRepository beautyServiceRepository;

    // ✅ SINGLE CONSTRUCTOR (CORRECT)
    public CustomerController(AppointmentService appointmentService,
                              AppointmentRepository appointmentRepo,
                              BeautyServiceRepository beautyServiceRepository) {
        this.appointmentService = appointmentService;
        this.appointmentRepo = appointmentRepo;
        this.beautyServiceRepository = beautyServiceRepository;
    }

    // =================================================
    // 1️⃣ GET ALL SERVICES (FROM DATABASE)
    // =================================================
    @GetMapping("/services")
    public List<BeautyService> getAllServices() {
        return beautyServiceRepository.findAll();
    }

    // =================================================
    // 2️⃣ CREATE APPOINTMENT (CUSTOMER FROM JWT)
    // =================================================
    @PostMapping("/appointment")
    public Appointment bookAppointment(
            @RequestBody CustomerBookingRequest request) {

        return appointmentService.createAppointmentForCustomer(request);
    }

    // =================================================
    // 3️⃣ VIEW MY APPOINTMENTS (JWT BASED)
    // =================================================
    @GetMapping("/appointments")
    public List<Appointment> getMyAppointments() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        return appointmentRepo.findByCustomerEmail(email);
    }

    // =================================================
    // 4️⃣ CANCEL APPOINTMENT
    // CREATED / ACCEPTED → CANCELLED
    // =================================================
    @PutMapping("/appointment/{appointmentId}/cancel")
    public Appointment cancelAppointment(
            @PathVariable Long appointmentId) {

        return appointmentService.cancelAppointment(appointmentId);
    }

    // =================================================
    // 5️⃣ VIEW SINGLE APPOINTMENT DETAILS
    // =================================================
    @GetMapping("/appointment/{appointmentId}")
    public Appointment getAppointmentDetails(
            @PathVariable Long appointmentId) {

        return appointmentRepo.findById(appointmentId)
                .orElseThrow(() ->
                        new RuntimeException("Appointment not found"));
    }

    // =================================================
    // 6️⃣ PAY FOR APPOINTMENT
    // =================================================
    @PostMapping("/payment/{invoiceId}")
    public Payment makePayment(
            @PathVariable Long invoiceId,
            @RequestParam String method) {

        return appointmentService.makePayment(invoiceId, method);
    }
}
