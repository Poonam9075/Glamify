package com.glamify.repository;

import com.glamify.entity.Appointment;
import com.glamify.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByCustomerUserId(Long userId);
    List<Appointment> findByProfessionalUserId(Long userId);
    List<Appointment> findByCustomerEmail(String email);
    List<Appointment> findByProfessionalIsNullAndStatus(AppointmentStatus status);
}
