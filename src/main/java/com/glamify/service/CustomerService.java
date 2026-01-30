package com.glamify.service;

import java.util.List;
import java.util.Optional;

import com.glamify.entity.Appointment;
import com.glamify.entity.Customer;

public interface CustomerService {

    Customer register(Customer customer);

    Optional<Customer> getByEmail(String email);

    List<Customer> getAllCustomers();
    
    public List<Appointment> getMyAppointments();
}
