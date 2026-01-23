package com.Glamify.services;

import org.springframework.stereotype.Service;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.ServiceCreateDTO;
import com.Glamify.entities.ServiceCategory;
import com.Glamify.entities.Services;
import com.Glamify.entities.Status;
import com.Glamify.exceptions.DuplicateResourceException;
import com.Glamify.exceptions.InvalidOperationException;
import com.Glamify.exceptions.ResourceNotFoundException;
import com.Glamify.repository.ServiceCategoryRepository;
import com.Glamify.repository.ServiceRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceCategoryRepository categoryRepository;

    public ApiResponse addService(ServiceCreateDTO dto) {

        if (serviceRepository.existsByServiceName(dto.getServiceName())) {
            throw new DuplicateResourceException(
                    "Service already exists");
        }

        ServiceCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        if (category.getStatus() != Status.ACTIVE) {
            throw new InvalidOperationException(
                    "Category is inactive");
        }

        Services service = new Services();
        service.setServiceName(dto.getServiceName());
        service.setPrice(dto.getPrice());
        service.setEstimatedTime(dto.getEstimatedTime());
        service.setCategory(category);

        serviceRepository.save(service);

        return new ApiResponse("SUCCESS", "Service added successfully");
    }
}
