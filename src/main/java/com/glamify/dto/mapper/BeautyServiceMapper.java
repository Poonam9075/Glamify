package com.glamify.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import com.glamify.dto.BeautyServiceDto;
import com.glamify.entity.BeautyService;

public class BeautyServiceMapper {

	public static BeautyServiceDto toDto(BeautyService beautyService)
	{
		BeautyServiceDto beautyServiceDto = new BeautyServiceDto();
		beautyServiceDto.setActive(beautyService.isActive());
		beautyServiceDto.setCategory(beautyService.getCategory());
		beautyServiceDto.setDiscount(beautyService.getDiscount());
		beautyServiceDto.setDuration(beautyService.getDuration());
		beautyServiceDto.setId(beautyService.getId());
		beautyServiceDto.setName(beautyService.getName());
		beautyServiceDto.setPrice(beautyService.getPrice());
		
		return beautyServiceDto;
	}
	
	public static List<BeautyServiceDto> toDtoList(List<BeautyService> beautyServiceList)
	{
		List<BeautyServiceDto> beautyServiceDtoList = new ArrayList<>();
		
		for(BeautyService beautyService : beautyServiceList)
		{
			beautyServiceDtoList.add(BeautyServiceMapper.toDto(beautyService));
		}
		
		return beautyServiceDtoList;
	}
	
	public static BeautyService toEntity(BeautyServiceDto beautyServiceDto)
	{
		BeautyService beautyService = new BeautyService();
		beautyService.setActive(beautyServiceDto.isActive());
		beautyService.setCategory(beautyServiceDto.getCategory());
		beautyService.setDiscount(beautyServiceDto.getDiscount());
		beautyService.setDuration(beautyServiceDto.getDuration());
		beautyService.setId(beautyServiceDto.getId());
		beautyService.setName(beautyServiceDto.getName());
		beautyService.setPrice(beautyServiceDto.getPrice());
		
		return beautyService;
	}
}
