package com.Glamify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.AppointmentCreateDTO;
import com.Glamify.services.AppointmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customer/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ApiResponse> bookAppointment(
            @RequestBody AppointmentCreateDTO request) {

        ApiResponse response = appointmentService.bookAppointment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
