package com.glamify.service;

import java.util.List;
import com.glamify.dto.AppointmentBookingRequest;
import com.glamify.dto.AppointmentDto;
import com.glamify.dto.AppointmentViewDto;
import com.glamify.entity.Appointment;
import com.glamify.entity.AppointmentStatus;
import com.glamify.entity.Payment;

public interface AppointmentService {

	AppointmentDto getAppointmentDetails(Long appointmentId);

	List<AppointmentViewDto> getAppointmentsByProfessional();
	
	AppointmentDto acceptAppointmentByProfessional(Long appointmentId);

	AppointmentDto updateStatus(Long appointmentId, AppointmentStatus newStatus);

	AppointmentDto cancelAppointment(Long id);

	//Payment makePayment(Long invoiceId, String method);

	AppointmentDto createAppointmentForCustomer(AppointmentBookingRequest req);

	List<AppointmentViewDto> getUnacceptedAppointments();

	List<AppointmentDto> getAllAppointments();

	void markPaymentPending(Appointment appointment);

	void confirmAppointment(Appointment appointment);

}
