package com.Glamify.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.ServiceCategoryCreateDTO;
import com.Glamify.dto.ServiceCategoryResponseDTO;
import com.Glamify.entities.ServiceCategory;
import com.Glamify.entities.Status;
import com.Glamify.exceptions.DuplicateResourceException;
import com.Glamify.repository.ServiceCategoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceCategoryServiceImple implements ServiceCategoryService {

    private final ServiceCategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ServiceCategoryResponseDTO> getAllCategories() {

        List<ServiceCategory> categories =
                categoryRepository.findAll();

        return categories.stream()
                .map(category ->
                        modelMapper.map(
                                category,
                                ServiceCategoryResponseDTO.class))
                .toList();
    }

    

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
