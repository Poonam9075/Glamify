package com.glamify.controller;

import com.glamify.entity.Payment;
import com.glamify.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final AppointmentService appointmentService;

    public PaymentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/{invoiceId}")
    public Payment makePayment(@PathVariable Long invoiceId,
                               @RequestParam String method) {
        return appointmentService.makePayment(invoiceId, method);
    }
}
