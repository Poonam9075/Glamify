package com.Glamify.services;

import org.springframework.stereotype.Service;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.ServiceCategoryCreateDTO;
import com.Glamify.entities.ServiceCategory;
import com.Glamify.entities.Status;
import com.Glamify.exceptions.DuplicateResourceException;
import com.Glamify.repository.ServiceCategoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceCategoryService {

    private final ServiceCategoryRepository categoryRepository;

    public ApiResponse addCategory(ServiceCategoryCreateDTO dto) {

        if (categoryRepository.existsByCategoryName(dto.getCategoryName())) {
            throw new DuplicateResourceException(
                    "Service category already exists");
        }

        ServiceCategory category = new ServiceCategory();
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        category.setStatus(Status.ACTIVE);

        categoryRepository.save(category);

        return new ApiResponse("SUCCESS", "Category added successfully");
    }
}
