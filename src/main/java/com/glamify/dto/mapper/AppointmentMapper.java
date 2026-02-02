package com.glamify.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.glamify.dto.AppointmentDto;
import com.glamify.entity.Appointment;
import com.glamify.entity.BookedService;

public class AppointmentMapper {
	
	public static AppointmentDto toDto(Appointment appointment)
	{
		AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setAppointmentId(appointment.getAppointmentId());
        appointmentDto.setCustomerId(appointment.getCustomer().getUserId());
        appointmentDto.setDateTime(appointment.getDateTime());
        appointmentDto.setLocation(appointment.getLocation());
        appointmentDto.setStatus(appointment.getStatus().toString());
        appointmentDto.setBookedServiceIds(appointment.getBookedServices().stream().map(BookedService::getId).toList());
        appointmentDto.setAmount(appointment.getAmount());
        
        if(appointment.getPayment() != null)
        	appointmentDto.setPaymentStatus(appointment.getPayment().getStatus().toString());
        
        return appointmentDto;
	}
	
	public static List<AppointmentDto> toDtoList(List<Appointment> appointmentList)
	{
		List<AppointmentDto> appointmentDtoList = new ArrayList<>();
		
		for(Appointment appointment : appointmentList)
		{
			appointmentDtoList.add(AppointmentMapper.toDto(appointment));
		}
		
		return appointmentDtoList;
	}
}
