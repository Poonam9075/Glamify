package com.glamify.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.glamify.dto.AppointmentViewDto;
import com.glamify.dto.CustomerDto;
import com.glamify.dto.mapper.AppointmentViewMapper;
import com.glamify.dto.mapper.CustomerMapper;
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
    public CustomerDto getByEmail(String email) {
    	
    	Optional<Customer> optCustomer = customerRepository.findByEmail(email);
    	Customer customer = optCustomer.orElseThrow();
    	
        return CustomerMapper.toDto(customer);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return CustomerMapper.toDtolist(customerRepository.findAll());
    }
    
    @Override
    public List<AppointmentViewDto> getMyAppointments() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        
        Customer customer = customerRepository.findByEmail(email).orElseThrow();
        
        List<Appointment> appointmentList = appointmentRepository.findByCustomerUserId(customer.getUserId());
        
        return AppointmentViewMapper.toViewDtoList(appointmentList);

    }
}
