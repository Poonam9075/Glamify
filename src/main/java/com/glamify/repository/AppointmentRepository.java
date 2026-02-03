package com.glamify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.glamify.entity.Appointment;
import com.glamify.entity.AppointmentStatus;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByCustomerUserId(Long userId);
    List<Appointment> findByProfessionalUserId(Long userId);
    List<Appointment> findByCustomerEmail(String email);
    List<Appointment> findByProfessionalIsNullAndStatus(AppointmentStatus status);
    
}
