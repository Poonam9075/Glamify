package com.glamify.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.glamify.dto.AppointmentResponse;
import com.glamify.dto.CustomerBookingRequest;
import com.glamify.entity.Appointment;
import com.glamify.entity.BeautyService;
import com.glamify.repository.AppointmentRepository;
import com.glamify.service.AppointmentService;
import com.glamify.service.BeautyServicesService;
import com.glamify.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	private final CustomerService customerService;
    private final AppointmentService appointmentService;
    private final BeautyServicesService beautyServicesService;

    public CustomerController(CustomerService customerService,
    						  AppointmentService appointmentService,
                              AppointmentRepository appointmentRepo,
                              BeautyServicesService beautyServicesService
                              ) {
        this.appointmentService = appointmentService;
        this.beautyServicesService = beautyServicesService;
        this.customerService = customerService;
    }

    // Get active available services
    @GetMapping("/services")
    public List<BeautyService> getActiveServices() {
    	
        return beautyServicesService.getActiveServices();
    }

    // Create appointment for customer
    @PostMapping("/appointment")
    public AppointmentResponse bookAppointment(
            @RequestBody CustomerBookingRequest request) {
    	
        return appointmentService.createAppointmentForCustomer(request);
    }

    // Get customer appointments
    @GetMapping("/appointments")
    public List<Appointment> getMyAppointments() {

        return customerService.getMyAppointments();

    }

    // Cancel appointment
    @PutMapping("/appointment/{appointmentId}/cancel")
    public Appointment cancelAppointment(
            @PathVariable Long appointmentId) {

        return appointmentService.cancelAppointment(appointmentId);
    }
    
}
