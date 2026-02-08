package com.Glamify.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.CustomerRegDTO;
import com.Glamify.dto.ServiceResponseDTO;
import com.Glamify.entities.Gender;
import com.Glamify.entities.Services;
import com.Glamify.services.CustomerService;
import com.Glamify.services.ServiceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final ServiceService serviceService;
    
//Customer Registration
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerCustomer(
            @RequestBody CustomerRegDTO request) {

        ApiResponse response = customerService.registerCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Search Service
    @GetMapping("/services/search")
    public ResponseEntity<List<Services>> searchServices(
            @RequestParam String serviceName) {

        return ResponseEntity.ok(customerService.searchServices(serviceName));
    }
    
// Search By Gender
    @GetMapping("/services/search-by-gender")
    public ResponseEntity<List<Services>> searchByGender(
            @RequestParam Gender gender) {

        return ResponseEntity.ok(customerService.searchServicesByGender(gender));
    }

// Filter By Price
    @GetMapping("/services/filter-by-price")
    public ResponseEntity<List<Services>> filterByPrice(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {

        return ResponseEntity.ok(
                customerService.filterServicesByPrice(minPrice, maxPrice));
    }

    // Get All Services
    @GetMapping("/services")
    public ResponseEntity<List<ServiceResponseDTO>> getAllServices() {
        return ResponseEntity.ok(
                serviceService.getAllServices()
        );
    }
}
