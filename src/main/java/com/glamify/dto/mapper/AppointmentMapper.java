package com.glamify.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.glamify.dto.AppointmentDto;
import com.glamify.dto.UserDto;
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

	public static List<AppointmentDto> toDtoListWithUsernames(List<Appointment> appointmentList,
			List<UserDto> usersList) 
	{
		List<AppointmentDto> appointmentDtoList = new ArrayList<>();
		
		for(Appointment appointment : appointmentList)
		{
			String customerName = usersList.stream().filter(u -> u.getUserId() == appointment.getCustomer().getUserId()).findFirst().get().getFullName();
			
			String professionalName = null;
			if(appointment.getProfessional() != null)
				professionalName = usersList.stream().filter(u -> u.getUserId() == appointment.getProfessional().getUserId()).findFirst().get().getFullName();

			AppointmentDto appointmentDto = AppointmentMapper.toDto(appointment);
			appointmentDto.setCustomerName(customerName);
			appointmentDto.setProfessionalName(professionalName);
			
			appointmentDtoList.add(appointmentDto);
		}
		
		return appointmentDtoList;
	}
}
