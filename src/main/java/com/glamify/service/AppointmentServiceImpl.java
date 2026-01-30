package com.glamify.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.glamify.dto.AppointmentResponse;
import com.glamify.dto.CustomerBookingRequest;
import com.glamify.entity.Appointment;
import com.glamify.entity.AppointmentStatus;
import com.glamify.entity.BeautyService;
import com.glamify.entity.BookedService;
import com.glamify.entity.Customer;
import com.glamify.entity.Invoice;
import com.glamify.entity.Payment;
import com.glamify.entity.Professional;
import com.glamify.exception.InvalidAppointmentStatusException;
import com.glamify.repository.AppointmentRepository;
import com.glamify.repository.BeautyServiceRepository;
import com.glamify.repository.BookedServiceRepository;
import com.glamify.repository.CustomerRepository;
import com.glamify.repository.InvoiceRepository;
import com.glamify.repository.PaymentRepository;
import com.glamify.repository.ProfessionalRepository;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    // ================== REPOSITORIES ==================
    private final AppointmentRepository appointmentRepo;
    private final BookedServiceRepository bookedServiceRepo;
    private final InvoiceRepository invoiceRepo;
    private final PaymentRepository paymentRepo;
    private final CustomerRepository customerRepo;
    private final ProfessionalRepository professionalRepo;
    private final BeautyServiceRepository serviceRepo;

    // ================== CONSTRUCTOR ==================
    public AppointmentServiceImpl(AppointmentRepository appointmentRepo,
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

    @Override
    public List<Appointment> getAllAppointments() {
    	return appointmentRepo.findAll();
    }
    
    
    @Override
    public List<Appointment> getAppointmentsByProfessional() {

            String email = SecurityContextHolder.getContext()
                    .getAuthentication().getName();
            
            Professional professional = professionalRepo.findByEmail(email).orElseThrow();
            
            return appointmentRepo.findByProfessionalUserId(professional.getUserId());

    }
    
    @Override
    public List<Appointment> getUnacceptedAppointments() {
        return appointmentRepo.findByProfessionalIsNullAndStatus(
                AppointmentStatus.CREATED
        );
    }
    
    
    // =================================================
    // 1️⃣ CUSTOMER BOOKING (AUTO-ASSIGN PROFESSIONAL)
    // =================================================
    @Override
    public AppointmentResponse createAppointmentForCustomer(CustomerBookingRequest req) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Customer customer = customerRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Appointment appointment = new Appointment();
        appointment.setCustomer(customer);
        appointment.setLocation(req.getLocation());
        appointment.setDateTime(req.getDateTime());
        appointment.setStatus(AppointmentStatus.CREATED);

        // 🚫 NO professional assignment here

        Appointment saved = appointmentRepo.save(appointment);

        double totalPrice = 0;        
        double totalDiscount = 0;
        double totalDiscountedPrice = 0;

        Map<String, Integer> serviceDiscountSnapshot = new HashMap<>();

        
        for (Long serviceId : req.getServiceIds()) {
            BeautyService service = serviceRepo.findById(serviceId)
                    .orElseThrow(() -> new RuntimeException("Service not found"));

            BookedService bs = new BookedService();
            bs.setAppointment(saved);
            bs.setBeautyService(service);
            bs.setPriceAtBooking(service.getPrice());
            bs.setEstimatedTime(service.getDuration());

            bookedServiceRepo.save(bs);
            
            double price = service.getPrice();
            int discount = service.getDiscount();
            double discountedPrice = price - (price * discount / 100.0);
            
            totalPrice += price;
            totalDiscount += discount;
            totalDiscountedPrice += discountedPrice;
            
            serviceDiscountSnapshot.put(service.getName(), service.getDiscount());
        }

        Invoice invoice = new Invoice();
        invoice.setAppointment(saved);
        invoice.setTotal(totalPrice);
        invoice.setCouponDiscount(totalDiscount);
        invoice.setFinalAmount(totalDiscountedPrice);
        invoice.setDateTime(LocalDateTime.now());
        invoiceRepo.save(invoice);

        AppointmentResponse response = new AppointmentResponse();
        response.setAppointmentId(saved.getAppointmentId());
        response.setCustomerId(saved.getCustomer().getUserId());
        response.setDateTime(saved.getDateTime());
        response.setLocation(saved.getLocation());
        response.setStatus(saved.getStatus());
        response.setBookedServices(req.getServiceIds());
        
        //return saved;
        return response;
    }

    @Override
    public Appointment getAppointmentDetails(Long appointmentId) {

        return appointmentRepo.findById(appointmentId)
                .orElseThrow(() ->
                        new RuntimeException("Appointment not found"));
    }
    
    @Override
    public Appointment acceptAppointmentByProfessional(Long appointmentId) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Professional professional = professionalRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Professional not found"));

        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointment.getProfessional() != null) {
            throw new RuntimeException("Appointment already assigned");
        }

        appointment.setProfessional(professional);
        appointment.setStatus(AppointmentStatus.ACCEPTED);

        return appointmentRepo.save(appointment);
    }


    // Update appointment status
    @Override
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
    // 5️⃣ CUSTOMER CANCEL
    // =================================================
    @Override
    public Appointment cancelAppointment(Long id) {
        return updateStatus(id, AppointmentStatus.CANCELLED);
    }

    // =================================================
    // 6️⃣ PAYMENT
    // =================================================
    @Override
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
