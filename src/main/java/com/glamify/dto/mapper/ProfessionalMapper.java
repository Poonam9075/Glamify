package com.glamify.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.glamify.dto.CustomerDto;
import com.glamify.dto.ProfessionalDto;
import com.glamify.entity.Customer;
import com.glamify.entity.Professional;

public class ProfessionalMapper {

	public static ProfessionalDto toDto(Professional professional)
	{
		ProfessionalDto professionalDto = new ProfessionalDto();
		
		professionalDto.setActive(professional.isActive());
		professionalDto.setEmail(professional.getEmail());
		professionalDto.setFullName(professional.getFullName());
		professionalDto.setGender(professional.getGender());
		professionalDto.setPassword(professional.getPassword());
		professionalDto.setPhone(professional.getPhone());
		professionalDto.setUserRole(professional.getRole());
		professionalDto.setUserId(professional.getUserId());
		
		return professionalDto;
	}
	
	public static List<ProfessionalDto> toDtolist(List<Professional> professionalList)
	{
		List<ProfessionalDto> professionalDtoList = new ArrayList<>();
		
		for(Professional professional : professionalList)
		{
			professionalDtoList.add(ProfessionalMapper.toDto(professional));
		}
		
		return professionalDtoList;
	}
	
	public static Professional toEntity(ProfessionalDto professionalDto)
	{
		Professional professional = new Professional();
		
		professional.setActive(professionalDto.isActive());
		professional.setEmail(professionalDto.getEmail());
		professional.setFullName(professionalDto.getFullName());
		professional.setGender(professionalDto.getGender());
		professional.setPassword(professionalDto.getPassword());
		professional.setPhone(professionalDto.getPhone());
		professional.setRole(professionalDto.getUserRole());
		professional.setUserId(professionalDto.getUserId());
		
		return professional;
	}
}
