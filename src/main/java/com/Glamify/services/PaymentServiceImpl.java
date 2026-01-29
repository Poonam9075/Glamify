package com.Glamify.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Glamify.dto.PaymentRequestDto;
import com.Glamify.dto.PaymentResponseDto;
import com.Glamify.entities.Appointment;
import com.Glamify.entities.AppointmentStatus;
import com.Glamify.entities.Payment;
import com.Glamify.entities.PaymentStatus;
import com.Glamify.repository.AppointmentRepository;
import com.Glamify.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    @Transactional
    public PaymentResponseDto makePayment(PaymentRequestDto request) {

        // 1️⃣ Fetch appointment
        Appointment appointment = appointmentRepository
                .findById(request.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // 2️⃣ Create Payment entity
        Payment payment = new Payment();
        payment.setAppointmentId(request.getAppointmentId());
        payment.setCustomerId(request.getCustomerId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentTime(LocalDateTime.now());
        payment.setStatus(PaymentStatus.SUCCESS);

        // 3️⃣ Save payment
        paymentRepository.save(payment);

        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);

        // 5️⃣ RETURN A PROPER RESPONSE
        return new PaymentResponseDto(
                "Payment successful",
                PaymentStatus.SUCCESS
        );
    }
}
   
