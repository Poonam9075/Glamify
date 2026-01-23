package com.Glamify.services;

import java.util.List;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.AppointmentCreateDTO;
import com.Glamify.dto.AppointmentResponseDTO;

public interface AppointmentService {

    ApiResponse bookAppointment(AppointmentCreateDTO request);
    List<AppointmentResponseDTO> getCustomerAppointments();
    
    List<AppointmentResponseDTO> getProfessionalAppointments();
    ApiResponse markAppointmentCompleted(Long appointmentId);
	ApiResponse cancelAppointment(Long appointmentId);
}
