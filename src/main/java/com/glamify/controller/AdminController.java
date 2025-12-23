package com.glamify.controller;

import com.glamify.entity.BeautyService;
import com.glamify.entity.Appointment;
import com.glamify.entity.Customer;
import com.glamify.entity.Professional;
import com.glamify.repository.*;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final CustomerRepository customerRepo;
    private final ProfessionalRepository professionalRepo;
    private final AppointmentRepository appointmentRepo;
    private final BeautyServiceRepository beautyServiceRepo;

    public AdminController(CustomerRepository customerRepo,
                           ProfessionalRepository professionalRepo,
                           AppointmentRepository appointmentRepo,
                           BeautyServiceRepository beautyServiceRepo) {
        this.customerRepo = customerRepo;
        this.professionalRepo = professionalRepo;
        this.appointmentRepo = appointmentRepo;
        this.beautyServiceRepo = beautyServiceRepo;
    }

    // ================= USERS =================

    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return customerRepo.findAll();
    }

    @GetMapping("/professionals")
    public List<Professional> getProfessionals() {
        return professionalRepo.findAll();
    }

    // ================= APPOINTMENTS =================

    @GetMapping("/appointments")
    public List<Appointment> getAppointments() {
        return appointmentRepo.findAll();
    }

    // ================= BEAUTY SERVICES =================

    @PostMapping("/beauty-service")
    public BeautyService addService(@RequestBody BeautyService beautyService) {
        return beautyServiceRepo.save(beautyService);
    }

    @GetMapping("/beauty-services")
    public List<BeautyService> getServices() {
        return beautyServiceRepo.findAll();
    }
}
