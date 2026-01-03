package com.glamify.service;

import com.glamify.dto.CustomerBookingRequest;
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

    // ================== REPOSITORIES ==================
    private final AppointmentRepository appointmentRepo;
    private final BookedServiceRepository bookedServiceRepo;
    private final InvoiceRepository invoiceRepo;
    private final PaymentRepository paymentRepo;
    private final CustomerRepository customerRepo;
    private final ProfessionalRepository professionalRepo;
    private final BeautyServiceRepository serviceRepo;

    // ================== CONSTRUCTOR ==================
    public AppointmentService(AppointmentRepository appointmentRepo,
                              BookedServiceRepository bookedServiceRepo,
                              InvoiceRepository invoiceRepo,
                              PaymentRepository paymentRepo,
                              CustomerRepository customerRepo,
                              ProfessionalRepository professionalRepo,
                              BeautyServiceRepository serviceRepo) {
        this.appointmentRepo = appointmentRepo;
        this.bookedServiceRepo = bookedServiceRepo;
        this.invoiceRepo = invoiceRepo;
        this.paymentRepo = paymentRepo;
        this.customerRepo = customerRepo;
        this.professionalRepo = professionalRepo;
        this.serviceRepo = serviceRepo;
    }

    // =================================================
    // 1️⃣ CUSTOMER BOOKING (AUTO-ASSIGN PROFESSIONAL)
    // =================================================
    public Appointment createAppointmentForCustomer(CustomerBookingRequest req) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Customer customer = customerRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Professional professional = professionalRepo.findAll().get(0);

        Appointment appointment = new Appointment();
        appointment.setCustomer(customer);
        appointment.setProfessional(professional);
        appointment.setLocation(req.getLocation());
        appointment.setDateTime(LocalDateTime.parse(req.getDateTime()));
        appointment.setStatus(AppointmentStatus.CREATED);

        Appointment saved = appointmentRepo.save(appointment);

        double total = 0;

        for (Long serviceId : req.getServiceIds()) {

            BeautyService service = serviceRepo.findById(serviceId)
                    .orElseThrow(() -> new RuntimeException("Service not found"));

            BookedService bs = new BookedService();
            bs.setAppointment(saved);
            bs.setBeautyService(service);
            bs.setPriceAtBooking(service.getPrice());
            bs.setEstimatedTime(service.getDuration());

            bookedServiceRepo.save(bs);

            total += service.getPrice();
        }

        Invoice invoice = new Invoice();
        invoice.setAppointment(saved);
        invoice.setTotal(total);
        invoice.setFinalAmount(total);
        invoice.setDateTime(LocalDateTime.now());
        invoiceRepo.save(invoice);

        return saved;
    }


    // =================================================
    // 2️⃣ OLD BOOKING STYLE (ADMIN / TESTING USE)
    // =================================================
    public Appointment createAppointment(Appointment appointment,
                                         List<BookedService> services) {

        Appointment savedAppointment = appointmentRepo.save(appointment);

        double total = 0;
        for (BookedService bs : services) {
            bs.setAppointment(savedAppointment);
            total += bs.getPriceAtBooking();
            bookedServiceRepo.save(bs);
        }

        Invoice invoice = new Invoice();
        invoice.setAppointment(savedAppointment);
        invoice.setTotal(total);
        invoice.setFinalAmount(total);
        invoice.setDateTime(LocalDateTime.now());
        invoiceRepo.save(invoice);

        return savedAppointment;
    }

    // =================================================
    // 3️⃣ UPDATE APPOINTMENT STATUS (STATE MACHINE)
    // =================================================
    public Appointment updateStatus(Long appointmentId,
                                    AppointmentStatus newStatus) {

        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        AppointmentStatus current = appointment.getStatus();

        // Validate transitions
        if (current == AppointmentStatus.CREATED &&
                (newStatus == AppointmentStatus.ACCEPTED ||
                 newStatus == AppointmentStatus.CANCELLED)) {

            appointment.setStatus(newStatus);

        } else if (current == AppointmentStatus.ACCEPTED &&
                (newStatus == AppointmentStatus.IN_PROGRESS ||
                 newStatus == AppointmentStatus.CANCELLED)) {

            appointment.setStatus(newStatus);

        } else if (current == AppointmentStatus.IN_PROGRESS &&
                newStatus == AppointmentStatus.COMPLETED) {

            appointment.setStatus(newStatus);

        } else {
            throw new InvalidAppointmentStatusException(
                    "Invalid status change from " + current + " to " + newStatus);
        }

        return appointmentRepo.save(appointment);
    }

    // =================================================
    // 4️⃣ PROFESSIONAL ACTIONS
    // =================================================
    public Appointment acceptAppointment(Long id) {
        return updateStatus(id, AppointmentStatus.ACCEPTED);
    }

    public Appointment startAppointment(Long id) {
        return updateStatus(id, AppointmentStatus.IN_PROGRESS);
    }

    public Appointment completeAppointment(Long id) {
        return updateStatus(id, AppointmentStatus.COMPLETED);
    }

    // =================================================
    // 5️⃣ CUSTOMER CANCEL
    // =================================================
    public Appointment cancelAppointment(Long id) {
        return updateStatus(id, AppointmentStatus.CANCELLED);
    }

    // =================================================
    // 6️⃣ PAYMENT
    // =================================================
    public Payment makePayment(Long invoiceId, String method) {

        Invoice invoice = invoiceRepo.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setAmount(invoice.getFinalAmount());
        payment.setPaymentMethod(method);
        payment.setStatus("SUCCESS");
        //payment.setPaymentDate(LocalDateTime.now());

        return paymentRepo.save(payment);
    }
}
