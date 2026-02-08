package com.Glamify.services;

import java.util.List;

import com.Glamify.dto.AdminAppointmentResponseDTO;
import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.AppointmentCreateDTO;
import com.Glamify.dto.AppointmentResponseDTO;
import com.Glamify.dto.PaymentRequestDto;
import com.Glamify.dto.PaymentResponseDto;
import com.Glamify.dto.RatingRequestDto;

import jakarta.validation.Valid;

public interface AppointmentService {

    ApiResponse bookAppointment(AppointmentCreateDTO request);
    List<AppointmentResponseDTO> getCustomerAppointments();
    
    List<AppointmentResponseDTO> getProfessionalAppointments();
    ApiResponse markAppointmentCompleted(Long appointmentId);
	ApiResponse cancelAppointment(Long appointmentId);
	 List<AdminAppointmentResponseDTO> getAllAppointmentsForAdmin();
	 
//	static PaymentResponseDto makePayment(PaymentRequestDto dto) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	void addRating(@Valid RatingRequestDto dto);
}
