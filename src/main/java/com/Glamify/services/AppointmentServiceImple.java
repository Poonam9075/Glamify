package com.Glamify.services;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.AppointmentCreateDTO;
import com.Glamify.entities.Appointment;
import com.Glamify.entities.AppointmentStatus;
import com.Glamify.entities.Customer;
import com.Glamify.entities.Professional;
import com.Glamify.entities.ProfessionalStatus;
import com.Glamify.entities.Services;
import com.Glamify.exceptions.InvalidOperationException;
import com.Glamify.exceptions.ResourceNotFoundException;
import com.Glamify.repository.AppointmentRepository;
import com.Glamify.repository.CustomerRepository;
import com.Glamify.repository.ProfessionalRepository;
import com.Glamify.repository.ServicesRepository;
import com.Glamify.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentServiceImple implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final CustomerRepository customerRepository;
    private final ProfessionalRepository professionalRepository;
    private final ServicesRepository serviceRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse bookAppointment(AppointmentCreateDTO request) {

        // 1️ Get logged-in customer
        Long userId = SecurityUtils.getLoggedInUserId();

        Customer customer = customerRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        // 2️ Validate appointment date
        if (request.getAppointmentDateTime().isBefore(LocalDateTime.now())) {
            throw new InvalidOperationException(
                    "Appointment date must be in the future");
        }

        // 3️ Validate professional
        Professional professional = professionalRepository
                .findById(request.getProfessionalId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Professional not found"));

        if (professional.getStatus() != ProfessionalStatus.APPROVED) {
            throw new InvalidOperationException(
                    "Professional is not approved yet");
        }

        // 4️ Validate service
        Services service = serviceRepository
                .findById(request.getServiceId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Service not found"));

        // 5️ Map DTO → Appointment entity
        Appointment appointment = modelMapper.map(request, Appointment.class);

        // 6️ Set system-controlled fields
        appointment.setCustomer(customer);
        appointment.setProfessional(professional);
        appointment.setService(service);
        appointment.setStatus(AppointmentStatus.BOOKED);

        // 7️ Save
        appointmentRepository.save(appointment);

        return new ApiResponse(
                "SUCCESS",
                "Appointment booked successfully"
        );
    }
}
