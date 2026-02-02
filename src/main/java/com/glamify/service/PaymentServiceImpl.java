package com.glamify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glamify.dto.PaymentDto;
import com.glamify.dto.PaymentGatewayRequest;
import com.glamify.dto.PaymentGatewayResponse;
import com.glamify.dto.mapper.PaymentMapper;
import com.glamify.entity.Appointment;
import com.glamify.entity.AppointmentStatus;
import com.glamify.entity.Invoice;
import com.glamify.entity.Payment;
import com.glamify.entity.PaymentStatus;
import com.glamify.repository.AppointmentRepository;
import com.glamify.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private PaymentGateway paymentGateway;

    @Override
    @Transactional
    public PaymentDto processPayment(Long appointmentId) {

        // ========= 1️⃣ FETCH APPOINTMENT =========
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // ========= 2️⃣ ENSURE PAYMENT EXISTS (🔥 FIX #1) =========
        // ❌ Earlier you were READING payment before this block
        Payment payment = appointment.getPayment();

        if (payment == null) {
            payment = new Payment();
            payment.setAppointment(appointment);
            appointment.setPayment(payment);   // 🔥 CRITICAL (bidirectional link)
            payment.setStatus(PaymentStatus.PENDING);
            payment.setAmount(appointment.getAmount());
            paymentRepository.save(payment);
        }

        // ========= 3️⃣ IDEMPOTENCY: ALREADY SUCCESS =========
        // (existing functionality preserved, just moved to SAFE position)
        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            return PaymentMapper.toDto(payment);
        }

        // ========= 4️⃣ CALL DUMMY PAYMENT GATEWAY =========
        PaymentGatewayResponse gatewayResponse =
                paymentGateway.processPayment(
                        new PaymentGatewayRequest(
                                appointment.getAppointmentId(),
                                appointment.getAmount(),
                                "INR"
                        )
                );

        // ========= 5️⃣ HANDLE GATEWAY RESULT =========
        if (gatewayResponse.isSuccess()) {

            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setTransactionId(gatewayResponse.getTransactionId());
            //payment.setPaidAt(LocalDateTime.now());

            // ========= 6️⃣ CREATE INVOICE ONLY ONCE (🔥 FIX #2) =========
            // ❌ Earlier invoice creation was happening even when payment was null
            if (payment.getInvoice() == null) {
                Invoice invoice = invoiceService.generateInvoice(
                        appointment,
                        payment
                );
                payment.setInvoice(invoice);
            }
            
            appointment.setStatus(AppointmentStatus.CONFIRMED);
            appointmentRepository.save(appointment);

        } else {

            payment.setStatus(PaymentStatus.FAILED);
            //payment.setFailureReason(gatewayResponse.getFailureReason());
        }

        // ========= 7️⃣ SAVE FINAL PAYMENT STATE =========
        paymentRepository.save(payment);

        // ========= 8️⃣ RESPONSE (UNCHANGED) =========
        return PaymentMapper.toDto(payment);
    }
}
