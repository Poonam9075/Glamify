package com.Glamify.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Glamify.dto.ServiceCategoryResponseDTO;
import com.Glamify.services.ServiceCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class ServiceCategoryController {
    private final ServiceCategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<ServiceCategoryResponseDTO>> getAllCategories() {

        return ResponseEntity.ok(
                categoryService.getAllCategories()
        );
    }
}
