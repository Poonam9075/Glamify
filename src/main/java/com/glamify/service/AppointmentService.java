package com.glamify.service;

import java.util.List;

import com.glamify.dto.AppointmentResponse;
import com.glamify.dto.CustomerBookingRequest;
import com.glamify.entity.Appointment;
import com.glamify.entity.AppointmentStatus;
import com.glamify.entity.Payment;

public interface AppointmentService {

	Appointment getAppointmentDetails(Long appointmentId);

	List<Appointment> getAppointmentsByProfessional();
	
	Appointment acceptAppointmentByProfessional(Long appointmentId);

	Appointment updateStatus(Long appointmentId, AppointmentStatus newStatus);

	Appointment cancelAppointment(Long id);

	Payment makePayment(Long invoiceId, String method);

	AppointmentResponse createAppointmentForCustomer(CustomerBookingRequest req);

	List<Appointment> getUnacceptedAppointments();

	List<Appointment> getAllAppointments();

}
