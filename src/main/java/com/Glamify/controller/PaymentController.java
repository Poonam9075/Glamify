package com.Glamify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Glamify.dto.PaymentRequestDto;
import com.Glamify.dto.PaymentResponseDto;
import com.Glamify.services.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public PaymentResponseDto pay(@Valid @RequestBody PaymentRequestDto dto) {
        return paymentService.makePayment(dto);
    }
}