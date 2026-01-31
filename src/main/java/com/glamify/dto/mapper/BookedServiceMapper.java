package com.glamify.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import com.glamify.dto.BookedServiceDto;
import com.glamify.entity.BookedService;

public class BookedServiceMapper {

	public static BookedServiceDto toDto(BookedService bookedService)
	{
		BookedServiceDto bookedServiceDto = new BookedServiceDto();
		
		bookedServiceDto.setAppointmentId(bookedService.getAppointment().getAppointmentId());
		bookedServiceDto.setBeautyServiceId(bookedService.getBeautyService().getId());
		bookedServiceDto.setEstimatedTime(bookedService.getEstimatedTime());
		bookedServiceDto.setId(bookedService.getId());
		bookedServiceDto.setPriceAtBooking(bookedService.getPriceAtBooking());
		
		return bookedServiceDto;
	}
	
	public static List<BookedServiceDto> toDtoList(List<BookedService> bookedServiceList)
	{
		List<BookedServiceDto> bookedServiceDtoList = new ArrayList<>();
		
		for(BookedService bookedService : bookedServiceList)
		{
			bookedServiceDtoList.add(BookedServiceMapper.toDto(bookedService));			
		}
		
		return bookedServiceDtoList;
	}
}
