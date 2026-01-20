package com.glamify.controller;

import com.glamify.entity.BeautyService;
import com.glamify.entity.Appointment;
import com.glamify.entity.Customer;
import com.glamify.entity.Professional;
import com.glamify.entity.User;
import com.glamify.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final UserRepository userRepo;
    private final CustomerRepository customerRepo;
    private final ProfessionalRepository professionalRepo;
    private final AppointmentRepository appointmentRepo;
    private final BeautyServiceRepository beautyServiceRepo;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepo,
    					   CustomerRepository customerRepo,
                           ProfessionalRepository professionalRepo,
                           AppointmentRepository appointmentRepo,
                           BeautyServiceRepository beautyServiceRepo) {
    	this.userRepo = userRepo;
        this.customerRepo = customerRepo;
        this.professionalRepo = professionalRepo;
        this.appointmentRepo = appointmentRepo;
        this.beautyServiceRepo = beautyServiceRepo;
    }

    // ================= USERS =================

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody User updatedUser) {

        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔒 Prevent admin from disabling himself (optional but recommended)
        String currentEmail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        if (existingUser.getEmail().equals(currentEmail)
                && !updatedUser.isActive()) {
            return ResponseEntity
                    .badRequest()
                    .body("Admin cannot disable own account");
        }

        // ✅ Update editable fields
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setUserRole(updatedUser.getUserRole());
        existingUser.setActive(updatedUser.isActive());

        // 🔑 Update password ONLY if provided
        if (updatedUser.getPassword() != null
                && !updatedUser.getPassword().isBlank()) {
            existingUser.setPassword(updatedUser.getPassword());
        }

        userRepo.save(existingUser);

        return ResponseEntity.ok(existingUser);
    }

    
    @PutMapping("/user/{id}/toggle")
    public ResponseEntity<?> toggleUser(@PathVariable Long id) {

        User user = userRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getEmail().equals(SecurityContextHolder
                .getContext().getAuthentication().getName())) {
            throw new RuntimeException("Admin cannot disable own account");
        }
        
        user.setActive(!user.isActive());
        userRepo.save(user);

        return ResponseEntity.ok(user);
    }

    
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

    // ================= GET ALL =================
    @GetMapping("/beauty-services")
    public List<BeautyService> getAllServices() {
        return beautyServiceRepo.findAll();
    }

    // ================= ADD =================
    @PostMapping("/beauty-service")
    public BeautyService addService(@RequestBody BeautyService service) {
        return beautyServiceRepo.save(service);
    }

    // ================= UPDATE =================
    @PutMapping("/beauty-service/{id}")
    public ResponseEntity<?> updateService(
            @PathVariable Long id,
            @RequestBody BeautyService updated) {

        BeautyService existing = beautyServiceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        existing.setName(updated.getName());
        existing.setCategory(updated.getCategory());
        existing.setPrice(updated.getPrice());
        existing.setDiscount(updated.getDiscount());
        existing.setDuration(updated.getDuration()); // ✅ IMPORTANT

        beautyServiceRepo.save(existing);
        return ResponseEntity.ok(existing);
    }

    // ================= DELETE =================
    @PutMapping("/beauty-service/{id}/toggle")
    public ResponseEntity<?> toggleService(@PathVariable Long id) {

        BeautyService service = beautyServiceRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Service not found"));

        service.setActive(!service.isActive()); // 🔥 toggle
        beautyServiceRepo.save(service);

        return ResponseEntity.ok(service);
    }

}
