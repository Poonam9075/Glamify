package com.glamify.service;

import java.util.List;
import java.util.Optional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.glamify.entity.Appointment;
import com.glamify.entity.Customer;
import com.glamify.repository.AppointmentRepository;
import com.glamify.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AppointmentRepository appointmentRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository,
    						   AppointmentRepository appointmentRepository) {
        this.customerRepository = customerRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Customer register(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> getByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    @Override
    public List<Appointment> getMyAppointments() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        
        Customer customer = customerRepository.findByEmail(email).orElseThrow();
        
        return appointmentRepository.findByCustomerUserId(customer.getUserId());

    }
}
