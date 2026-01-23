package com.Glamify.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.ServiceCategoryCreateDTO;
import com.Glamify.services.ServiceCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final ServiceCategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse> addCategory(
            @RequestBody ServiceCategoryCreateDTO dto) {

        return ResponseEntity.ok(categoryService.addCategory(dto));
    }
}
