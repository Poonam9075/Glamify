package com.Glamify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.CustomerRegDTO;
import com.Glamify.services.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/register")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/customer")
    public ResponseEntity<ApiResponse> registerCustomer(
            @RequestBody CustomerRegDTO request) {

        ApiResponse response = customerService.registerCustomer(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
