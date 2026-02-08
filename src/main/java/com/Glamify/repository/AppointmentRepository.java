package com.Glamify.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Glamify.entities.Appointment;
import com.Glamify.entities.Customer;
import com.Glamify.entities.Professional;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	List<Appointment> findByCustomer(Customer customer);
	List<Appointment> findByProfessional(Professional professional);
	Optional<Appointment> findByIdAndCustomerUserId(Long appointmentId, Long userId);
}

