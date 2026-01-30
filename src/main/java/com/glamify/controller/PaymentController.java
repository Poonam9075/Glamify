package com.glamify.controller;

import com.glamify.entity.Payment;
import com.glamify.service.AppointmentServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final AppointmentServiceImpl appointmentService;

    public PaymentController(AppointmentServiceImpl appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/{invoiceId}")
    public Payment makePayment(@PathVariable Long invoiceId,
                               @RequestParam String method) {
        return appointmentService.makePayment(invoiceId, method);
    }
        
}
