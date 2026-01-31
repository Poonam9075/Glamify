package com.glamify.dto.mapper;

import com.glamify.dto.BeautyServiceViewDto;
import com.glamify.entity.BeautyService;

public class BeautyServiceViewMapper {

	public static BeautyServiceViewDto toDto(BeautyService beautyService)
	{
		BeautyServiceViewDto beautyServiceViewDto = new BeautyServiceViewDto();
		beautyServiceViewDto.setEstimatedTime(beautyService.getDuration());
		beautyServiceViewDto.setServiceName(beautyService.getName());
		
		return beautyServiceViewDto;
	}
	
}
