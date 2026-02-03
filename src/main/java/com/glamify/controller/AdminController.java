package com.glamify.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.glamify.dto.AppointmentDto;
import com.glamify.dto.BeautyServiceDto;
import com.glamify.dto.CustomerDto;
import com.glamify.dto.PendingProfessionalResponse;
import com.glamify.dto.ProfessionalDto;
import com.glamify.dto.UserDto;
import com.glamify.dto.mapper.AdminStatsDto;
import com.glamify.service.AdminService;
import com.glamify.service.AppointmentService;
import com.glamify.service.BeautyServicesService;
import com.glamify.service.CustomerService;
import com.glamify.service.ProfessionalService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final AdminService adminService;
	private final CustomerService customerService;
	private final ProfessionalService professionalService;
	private final AppointmentService appointmentService;
	private final BeautyServicesService beautyServicesService;
	
    public AdminController(AdminService adminService,
    					   CustomerService customerService,
    					   ProfessionalService professionalService,
    					   AppointmentService appointmentService,
    					   BeautyServicesService beautyServicesService) {
        
        this.adminService = adminService;
        this.customerService = customerService;
        this.professionalService = professionalService;
        this.appointmentService = appointmentService;
        this.beautyServicesService = beautyServicesService;
    }

    
    // ================= USERS =================

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(
          @PathVariable Long id,
          @RequestBody UserDto updatedUser) {
    	
    	return ResponseEntity.ok(adminService.updateUser(id, updatedUser));
	}
        
    @PutMapping("/user/{id}/toggle")
    public ResponseEntity<?> toggleUser(@PathVariable Long id) {
    	
    	return ResponseEntity.ok(adminService.toggleUser(id));
    }    
    
    
    // ================= PROFESSIONAL =================
    
    @GetMapping("/professionals/pending")
    public List<PendingProfessionalResponse> getPendingProfessionals() {
    	return adminService.getPendingProfessionals();
    }
    
    @PutMapping("/professionals/{id}/approve")
    public ResponseEntity<String> approveProfessional(@PathVariable Long id) {
	    adminService.approveProfessional(id);
	    return ResponseEntity.ok("Professional approved successfully");
    }
    
    @GetMapping("/professionals")
    public List<ProfessionalDto> getProfessionals() {
        return professionalService.getAllProfessionals();
    }
    

    // ================= CUSTOMER =================        
    
    @GetMapping("/customers")
    public List<CustomerDto> getCustomers() {
        return customerService.getAllCustomers();        
    }
    

    // ================= APPOINTMENTS =================

    @GetMapping("/appointments")
    public List<AppointmentDto> getAppointments() {
        return appointmentService.getAllAppointmentsAdmin();
    }

    
    // ================= BEAUTY SERVICES =================

    // Get all beauty services
    @GetMapping("/beauty-services")
    public List<BeautyServiceDto> getAllServices() {
        return beautyServicesService.getAllServices();
    }

    // Add new beauty service
    @PostMapping("/beauty-service")
    public BeautyServiceDto addService(@RequestBody BeautyServiceDto beautyServiceDto) {
        return beautyServicesService.addService(beautyServiceDto);
    }

    // Update beauty service
    @PutMapping("/beauty-service/{id}")
    public ResponseEntity<BeautyServiceDto> updateService(
	          @PathVariable Long id,
	          @RequestBody BeautyServiceDto updated) {
    	return ResponseEntity.ok(beautyServicesService.updateService(id, updated));
    }
    
    // Enable/Disable beauty service
    @PutMapping("/beauty-service/{id}/toggle")
    public ResponseEntity<BeautyServiceDto> toggleService(@PathVariable Long id) {
    	return ResponseEntity.ok(beautyServicesService.toggleService(id));
    }
    
    @GetMapping("/dashboard-stats")
    public AdminStatsDto getAdminStats() {
        return adminService.getAdminStats();
    }
    

}
