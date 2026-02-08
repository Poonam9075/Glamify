package com.Glamify.services;

import java.util.List;

import com.Glamify.dto.ServiceCategoryResponseDTO;

public interface ServiceCategoryService {

    List<ServiceCategoryResponseDTO> getAllCategories();
}
