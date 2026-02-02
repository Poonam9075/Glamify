package com.glamify.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.glamify.dto.PaymentDto;
import com.glamify.entity.Payment;
import com.glamify.service.AppointmentServiceImpl;
import com.glamify.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final AppointmentServiceImpl appointmentService;
    private final PaymentService paymentService;

    public PaymentController(AppointmentServiceImpl appointmentService, PaymentService paymentService) {
        this.appointmentService = appointmentService;
        this.paymentService = paymentService;
    }

//    @PostMapping("/{invoiceId}")
//    public Payment makePayment(@PathVariable Long invoiceId,
//                               @RequestParam String method) {
//        return appointmentService.makePayment(invoiceId, method);
//    }
    
    @PostMapping("/payment/{appointmentId}")
    public PaymentDto initiate(@PathVariable Long appointmentId) {
        return paymentService.processPayment(appointmentId);
    }
        
}
