package com.glamify.service;

import java.util.List;

import com.glamify.dto.AppointmentViewDto;
import com.glamify.dto.CustomerDto;
import com.glamify.entity.Customer;

public interface CustomerService {

    Customer register(Customer customer);

    CustomerDto getByEmail(String email);

    List<CustomerDto> getAllCustomers();
    
    public List<AppointmentViewDto> getMyAppointments();
}
