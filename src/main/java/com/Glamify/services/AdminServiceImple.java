package com.Glamify.services;

import org.springframework.stereotype.Service;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.ServiceCreateDTO;
import com.Glamify.entities.ServiceCategory;
import com.Glamify.entities.Services;
import com.Glamify.entities.Status;
import com.Glamify.exceptions.InvalidOperationException;
import com.Glamify.exceptions.ResourceNotFoundException;
import com.Glamify.repository.ServiceCategoryRepository;
import com.Glamify.repository.ServiceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImple implements AdminService {

    private final ServiceRepository serviceRepository;
    private final ServiceCategoryRepository categoryRepository;

    @Override
    public ApiResponse updateService(Long serviceId, ServiceCreateDTO dto) {

        Services service = serviceRepository.findById(serviceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Service not found"));

        ServiceCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        if (category.getStatus() != Status.ACTIVE) {
            throw new InvalidOperationException(
                    "Cannot assign inactive category");
        }

        service.setServiceName(dto.getServiceName());
        service.setPrice(dto.getPrice());
        service.setEstimatedTime(dto.getEstimatedTime());
        service.setCategory(category);

        serviceRepository.save(service);

        return new ApiResponse("SUCCESS", "Service updated successfully");
    }
}
