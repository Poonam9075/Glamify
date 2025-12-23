package com.glamify.service;

import com.glamify.entity.*;
import com.glamify.exception.InvalidAppointmentStatusException;
import com.glamify.repository.*;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final BookedServiceRepository bookedServiceRepo;
    private final InvoiceRepository invoiceRepo;
    private final PaymentRepository paymentRepo;
    private final CustomerRepository customerRepo;
    private final ProfessionalRepository professionalRepo;

    public AppointmentService(AppointmentRepository appointmentRepo,
                              BookedServiceRepository bookedServiceRepo,
                              InvoiceRepository invoiceRepo,
                              PaymentRepository paymentRepo,
                              CustomerRepository customerRepo,
                              ProfessionalRepository professionalRepo) {
        this.appointmentRepo = appointmentRepo;
        this.bookedServiceRepo = bookedServiceRepo;
        this.invoiceRepo = invoiceRepo;
        this.paymentRepo = paymentRepo;
        this.customerRepo = customerRepo;
        this.professionalRepo = professionalRepo;
    }

    // =========================================================
    // 1️⃣ CREATE APPOINTMENT (CUSTOMER FROM JWT)
    // =========================================================
	public Appointment createAppointment(Appointment appointment, List<BookedService> services) {

		// ===============================
		// 1️⃣ CUSTOMER FROM JWT (SAFE)
		// ===============================
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		Customer customer = customerRepo.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Logged-in user is not a CUSTOMER"));

		appointment.setCustomer(customer);

		// ===============================
		// 2️⃣ PROFESSIONAL FROM DB (SAFE)
		// ===============================
		Long professionalId = appointment.getProfessional().getUserId();

		Professional professional = professionalRepo.findById(professionalId)
				.orElseThrow(() -> new RuntimeException("Invalid Professional ID"));

		appointment.setProfessional(professional);

		// ===============================
		// 3️⃣ STATUS
		// ===============================
		appointment.setStatus(AppointmentStatus.CREATED);

		// ===============================
		// 4️⃣ SAVE APPOINTMENT
		// ===============================
		Appointment saved = appointmentRepo.save(appointment);

		// ===============================
		// 5️⃣ BOOKED SERVICES
		// ===============================
		double total = 0;
		for (BookedService bs : services) {
			bs.setAppointment(saved);
			total += bs.getPriceAtBooking();
			bookedServiceRepo.save(bs);
		}

		// ===============================
		// 6️⃣ INVOICE
		// ===============================
		Invoice invoice = new Invoice();
		invoice.setAppointment(saved);
		invoice.setTotal(total);
		invoice.setFinalAmount(total);
		invoice.setDateTime(LocalDateTime.now());
		invoiceRepo.save(invoice);

		return saved;
	}


    // =========================================================
    // 2️⃣ UPDATE APPOINTMENT STATUS
    // (Customer / Professional / Admin)
    // =========================================================
    public Appointment updateStatus(Long appointmentId,
                                    AppointmentStatus newStatus) {

        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() ->
                        new RuntimeException("Appointment not found"));

        validateStatusTransition(
                appointment.getStatus(),
                newStatus
        );

        appointment.setStatus(newStatus);
        return appointmentRepo.save(appointment);
    }

    // =========================================================
    // 3️⃣ MAKE PAYMENT (AFTER COMPLETION)
    // =========================================================
    public Payment makePayment(Long invoiceId, String method) {

        Invoice invoice = invoiceRepo.findById(invoiceId)
                .orElseThrow(() ->
                        new RuntimeException("Invoice not found"));

        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setPaymentMethod(method);
        payment.setAmount(invoice.getFinalAmount());
        payment.setStatus("SUCCESS");

        return paymentRepo.save(payment);
    }

    // =========================================================
    // 4️⃣ STATUS VALIDATION (BUSINESS RULES)
    // =========================================================
    private void validateStatusTransition(AppointmentStatus current,
                                          AppointmentStatus next) {

        switch (current) {

            case CREATED -> {
                if (next != AppointmentStatus.ACCEPTED &&
                    next != AppointmentStatus.CANCELLED) {
                    throw new InvalidAppointmentStatusException(
                        "CREATED → ACCEPTED or CANCELLED only");
                }
            }

            case ACCEPTED -> {
                if (next != AppointmentStatus.IN_PROGRESS &&
                    next != AppointmentStatus.CANCELLED) {
                    throw new InvalidAppointmentStatusException(
                        "ACCEPTED → IN_PROGRESS or CANCELLED only");
                }
            }

            case IN_PROGRESS -> {
                if (next != AppointmentStatus.COMPLETED) {
                    throw new InvalidAppointmentStatusException(
                        "IN_PROGRESS → COMPLETED only");
                }
            }

            case COMPLETED, CANCELLED -> {
                throw new InvalidAppointmentStatusException(
                        current + " is a final state");
            }
        }
    }
}
