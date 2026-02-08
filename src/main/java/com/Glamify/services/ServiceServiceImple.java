package com.Glamify.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Glamify.dto.ServiceResponseDTO;
import com.Glamify.entities.Services;
import com.Glamify.repository.ServiceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceServiceImple implements ServiceService {

    private final ServiceRepository serviceRepository;
    
    

    @Override
    public List<ServiceResponseDTO> getAllServices() {

        List<Services> services = serviceRepository.findAll();

        return services.stream()
                .map(service -> {
                    ServiceResponseDTO dto = new ServiceResponseDTO();
                    dto.setId(service.getId());
                    dto.setServiceName(service.getServiceName());
                    dto.setPrice(service.getPrice());
                    dto.setEstimatedTime(service.getEstimatedTime());

                    dto.setCategoryId(
                            service.getCategory().getId());
                    dto.setCategoryName(
                            service.getCategory().getCategoryName());

                    return dto;
                })
                .toList();
    }
}

