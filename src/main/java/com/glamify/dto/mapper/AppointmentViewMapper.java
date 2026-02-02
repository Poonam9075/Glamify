package com.glamify.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.glamify.dto.AppointmentViewDto;
import com.glamify.dto.BeautyServiceViewDto;
import com.glamify.entity.Appointment;
import com.glamify.entity.BookedService;

public class AppointmentViewMapper {

    public static AppointmentViewDto toViewDto(Appointment appointment) {

        AppointmentViewDto dto = new AppointmentViewDto();
        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setDateTime(appointment.getDateTime());
        dto.setLocation(appointment.getLocation());
        dto.setStatus(appointment.getStatus().toString());
        dto.setAmount(appointment.getAmount());
        
        dto.setCustomerName(
            appointment.getCustomer() != null
                ? appointment.getCustomer().getFullName()
                : null
        );

        dto.setProfessionalName(
            appointment.getProfessional() != null
                ? appointment.getProfessional().getFullName()
                : null
        );

        List<BeautyServiceViewDto> beautyServiceViewDtoList = new ArrayList<>();
        
        for(BookedService bs : appointment.getBookedServices())
        {
        	beautyServiceViewDtoList.add(BeautyServiceViewMapper.toDto(bs.getBeautyService()));
        }
        
        dto.setServices(beautyServiceViewDtoList);

        // ✅ total time
        int totalTime = beautyServiceViewDtoList.stream()
                .mapToInt(BeautyServiceViewDto::getEstimatedTime)
                .sum();

        dto.setTotalEstimatedTime(totalTime);
        
        if(appointment.getPayment() != null && appointment.getPayment().getInvoice() != null)
        {
        	dto.setInvoiceNumber(appointment.getPayment().getInvoice().getInvoiceNumber());
        	dto.setPaymentStatus(appointment.getPayment().getStatus().toString());
        }

        return dto;
    }
    
    
    public static List<AppointmentViewDto> toViewDtoList(List<Appointment> appointmentList) {
    	
    	List<AppointmentViewDto> viewDtoList = new ArrayList<>();
    	
    	for(Appointment appointment : appointmentList)
    	{
    		viewDtoList.add(AppointmentViewMapper.toViewDto(appointment));
    	}
    	
    	return viewDtoList;
    }
}
