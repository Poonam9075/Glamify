package com.Glamify.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Glamify.dto.ServiceResponseDto;
import com.Glamify.entities.Services;
import com.Glamify.repository.ServiceRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    @Override
    public List<ServiceResponseDto> getAllServicesForCustomer() {

        List<Services> services = serviceRepository.findAll();

        return services.stream().map(service -> {
            ServiceResponseDto dto = new ServiceResponseDto();

            dto.setServiceId(service.getId());
            dto.setServiceName(service.getServiceName());
            dto.setPrice(service.getPrice());
            dto.setCategoryId(service.getCategory().getId());
            dto.setCategoryName(service.getCategory().getCategoryName());

            return dto;
        }).toList();
    }
}

