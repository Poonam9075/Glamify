package com.Glamify.services;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Glamify.dto.AdminAppointmentResponseDTO;
import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.AppointmentCreateDTO;
import com.Glamify.dto.AppointmentResponseDTO;
import com.Glamify.dto.ProfessionalResponseDTO;
import com.Glamify.dto.RatingRequestDto;
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
import com.Glamify.repository.ServiceRepository;
import com.Glamify.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentServiceImple implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final CustomerRepository customerRepository;
    private final ProfessionalRepository professionalRepository;
    private final ServiceRepository serviceRepository;
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

    @Override
    public List<AppointmentResponseDTO> getCustomerAppointments() {

        // 1️ Get logged-in customer
        Long userId = SecurityUtils.getLoggedInUserId();

        Customer customer = customerRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        // 2️ Fetch appointments
        List<Appointment> appointments =
                appointmentRepository.findByCustomer(customer);

        // 3️ Map to DTO
        return appointments.stream()
                .map(appointment -> {
                    AppointmentResponseDTO dto = new AppointmentResponseDTO();
                    dto.setAppointmentId(appointment.getId());
                    dto.setCustomerName(
                            appointment.getCustomer().getUser().getFirstName());
                    dto.setProfessionalName(
                            appointment.getProfessional().getUser().getFirstName());
                    dto.setServiceName(
                            appointment.getService().getServiceName());
                    dto.setAppointmentDateTime(
                            appointment.getAppointmentDateTime());
                    dto.setServiceAddress(
                            appointment.getServiceAddress());
                    dto.setStatus(
                            appointment.getStatus());
                    return dto;
                })
                .toList();
    }

    
    @Override
    public List<AppointmentResponseDTO> getProfessionalAppointments() {

        // 1️ Get logged-in professional
        Long userId = SecurityUtils.getLoggedInUserId();

        Professional professional = professionalRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Professional not found"));

        // 2️ Fetch appointments
        List<Appointment> appointments =
                appointmentRepository.findByProfessional(professional);

        // 3️ Manual mapping (flattened DTO)
        return appointments.stream()
                .map(appointment -> {
                    AppointmentResponseDTO dto = new AppointmentResponseDTO();
                    dto.setAppointmentId(appointment.getId());
                    dto.setCustomerName(
                            appointment.getCustomer().getUser().getFirstName());
                    dto.setProfessionalName(
                            professional.getUser().getFirstName());
                    dto.setServiceName(
                            appointment.getService().getServiceName());
                    dto.setAppointmentDateTime(
                            appointment.getAppointmentDateTime());
                    dto.setServiceAddress(
                            appointment.getServiceAddress());
                    dto.setStatus(
                            appointment.getStatus());
                    return dto;
                })
                .toList();
    }
    
    @Override
    public ApiResponse markAppointmentCompleted(Long appointmentId) {

        Long userId = SecurityUtils.getLoggedInUserId();

        Professional professional = professionalRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Professional not found"));

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment not found"));

        // ownership check
        if (!appointment.getProfessional().getId()
                .equals(professional.getId())) {
            throw new InvalidOperationException(
                    "You are not authorized to update this appointment");
        }

        // status check
        if (appointment.getStatus() != AppointmentStatus.BOOKED) {
            throw new InvalidOperationException(
                    "Only BOOKED appointments can be completed");
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);

        return new ApiResponse(
                "SUCCESS",
                "Appointment marked as completed");
    }
    
    //--------------- Cancel appointment By Customer -------------------------
    @Override
    public ApiResponse cancelAppointment(Long appointmentId) {

        // 1️ Get logged-in user id
        Long userId = SecurityUtils.getLoggedInUserId();

        // 2️ Fetch appointment for THIS customer only
        Appointment appointment = appointmentRepository
                .findByIdAndCustomerUserId(appointmentId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment not found"));

        // 3️ Validate status
        if (appointment.getStatus() != AppointmentStatus.BOOKED) {
            throw new InvalidOperationException(
                    "Only BOOKED appointments can be cancelled");
        }

        // 4️ Validate time
        if (appointment.getAppointmentDateTime().isBefore(LocalDateTime.now())) {
            throw new InvalidOperationException(
                    "Cannot cancel past appointments");
        }

        // 5️ Cancel appointment
        appointment.setStatus(AppointmentStatus.CANCELLED);

        return new ApiResponse(
                "SUCCESS",
                "Appointment cancelled successfully"
        );
    }
    
    
    //------ To show professional to customers while booking appointment -----------
    public List<ProfessionalResponseDTO> getProfessionalsForService(Long serviceId) {

        Services service = serviceRepository.findById(serviceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Service not found"));

        List<Professional> professionals =
                professionalRepository.findApprovedProfessionals(
                        ProfessionalStatus.APPROVED,
                        service.getServiceName()   // or category-based
                );

        return professionals.stream()
                .map(p -> {
                    ProfessionalResponseDTO dto = new ProfessionalResponseDTO();
                    dto.setProfessionalId(p.getId());
                    dto.setName(p.getUser().getFirstName());
                    dto.setSpeciality(p.getSpeciality());
                    dto.setExperienceInYears(p.getExperienceInYears());
                    return dto;
                }).toList();
    }

// --------- Admin Get All Appointments ---------------
    @Override
    public List<AdminAppointmentResponseDTO> getAllAppointmentsForAdmin() {

        return appointmentRepository.findAll()
                .stream()
                .map(appointment -> {
                    AdminAppointmentResponseDTO dto =
                            new AdminAppointmentResponseDTO();

                    dto.setAppointmentId(appointment.getId());

                    dto.setCustomerName(
                            appointment.getCustomer()
                                       .getUser()
                                       .getFirstName()
                    );

                    dto.setProfessionalName(
                            appointment.getProfessional()
                                       .getUser()
                                       .getFirstName()
                    );

                    dto.setServiceName(
                            appointment.getService()
                                       .getServiceName()
                    );

                    dto.setAppointmentDateTime(
                            appointment.getAppointmentDateTime()
                    );

                    dto.setServiceAddress(
                            appointment.getServiceAddress()
                    );

                    dto.setStatus(
                            appointment.getStatus()
                    );

                    return dto;
                })
                .toList();
    }
    
    @Override
    public void addRating(RatingRequestDto dto) {

        Appointment appointment = appointmentRepository
                .findById(dto.getAppointmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment not found"));

        // ✅ THIS CHECK BELONGS HERE
        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new InvalidOperationException("Service not completed yet");
        }

        appointment.setRating(dto.getRating());
        appointmentRepository.save(appointment);
    }



}
