package com.Glamify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Glamify.entities.Appointment;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // No extra code needed
}

