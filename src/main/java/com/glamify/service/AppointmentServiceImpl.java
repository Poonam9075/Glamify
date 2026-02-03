package com.glamify.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glamify.dto.AppointmentBookingRequest;
import com.glamify.dto.AppointmentDto;
import com.glamify.dto.AppointmentViewDto;
import com.glamify.dto.UserDto;
import com.glamify.dto.mapper.AppointmentMapper;
import com.glamify.dto.mapper.AppointmentViewMapper;
import com.glamify.dto.mapper.UserMapper;
import com.glamify.entity.Appointment;
import com.glamify.entity.AppointmentStatus;
import com.glamify.entity.BeautyService;
import com.glamify.entity.BookedService;
import com.glamify.entity.Customer;
import com.glamify.entity.Invoice;
import com.glamify.entity.Payment;
import com.glamify.entity.PaymentStatus;
import com.glamify.entity.Professional;
import com.glamify.exception.InvalidAppointmentStatusException;
import com.glamify.repository.AppointmentRepository;
import com.glamify.repository.BeautyServiceRepository;
import com.glamify.repository.BookedServiceRepository;
import com.glamify.repository.CustomerRepository;
import com.glamify.repository.InvoiceRepository;
import com.glamify.repository.PaymentRepository;
import com.glamify.repository.ProfessionalRepository;
import com.glamify.repository.UserRepository;

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
    private final UserRepository userRepository;
    
    // ================== CONSTRUCTOR ==================
    public AppointmentServiceImpl(AppointmentRepository appointmentRepo,
                              BookedServiceRepository bookedServiceRepo,
                              InvoiceRepository invoiceRepo,
                              PaymentRepository paymentRepo,
                              CustomerRepository customerRepo,
                              ProfessionalRepository professionalRepo,
                              BeautyServiceRepository serviceRepo,
                              UserRepository userRepository) {
        this.appointmentRepo = appointmentRepo;
        this.bookedServiceRepo = bookedServiceRepo;
        this.invoiceRepo = invoiceRepo;
        this.paymentRepo = paymentRepo;
        this.customerRepo = customerRepo;
        this.professionalRepo = professionalRepo;
        this.serviceRepo = serviceRepo;
        this.userRepository = userRepository;
    }

    @Override
    public List<AppointmentDto> getAllAppointments() {
    	
    	List<Appointment> appointmentList = appointmentRepo.findAll();    	
    	    	
    	return AppointmentMapper.toDtoList(appointmentList);
    }
    
    @Override
    public List<AppointmentDto> getAllAppointmentsAdmin() {
    	
    	List<Appointment> appointmentList = appointmentRepo.findAll();    	
    	List<UserDto> usersList = UserMapper.toDtolist(userRepository.findAll());
    	
    	return AppointmentMapper.toDtoListWithUsernames(appointmentList, usersList);
    }
    
    
    @Override
    public List<AppointmentViewDto> getAppointmentsByProfessional() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        
        Professional professional = professionalRepo.findByEmail(email).orElseThrow();
        
        List<Appointment> appointmentList = appointmentRepo.findByProfessionalUserId(professional.getUserId());
        
        return AppointmentViewMapper.toViewDtoList(appointmentList);
    }
    
    @Override
    public List<AppointmentViewDto> getUnacceptedAppointments() {
        
        List<Appointment> appointmentList = appointmentRepo.findByProfessionalIsNullAndStatus(
									                AppointmentStatus.CONFIRMED);
        
        return AppointmentViewMapper.toViewDtoList(appointmentList);
    }
    
    
    // =================================================
    // 1️⃣ CUSTOMER BOOKING
    // =================================================
    @Override
    public AppointmentDto createAppointmentForCustomer(AppointmentBookingRequest req) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Customer customer = customerRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Appointment appointment = new Appointment();
        appointment.setCustomer(customer);
        appointment.setLocation(req.getLocation());
        appointment.setDateTime(req.getDateTime());
        appointment.setStatus(AppointmentStatus.REQUESTED);
        
        // 🚫 NO professional assignment here

        //appointmentRepo.save(appointment);

        double totalPrice = 0;        
        double totalDiscount = 0;
        double totalDiscountedPrice = 0;

        Map<String, Integer> serviceDiscountSnapshot = new HashMap<>();
        List<BookedService> bookedServices = new ArrayList<>();
        
        for (Long serviceId : req.getServiceIds()) {
            BeautyService service = serviceRepo.findById(serviceId)
                    .orElseThrow(() -> new RuntimeException("Service not found"));

            BookedService bs = new BookedService();
            bs.setAppointment(appointment);
            bs.setBeautyService(service);
            bs.setPriceAtBooking(service.getPrice());
            bs.setEstimatedTime(service.getDuration());
            bookedServices.add(bs);
            
            bookedServiceRepo.save(bs);
            
            double price = service.getPrice();
            int discount = service.getDiscount();
            double discountedPrice = price - (price * discount / 100.0);
            
            totalPrice += price;
            totalDiscount += discount;
            totalDiscountedPrice += discountedPrice;
            
            serviceDiscountSnapshot.put(service.getName(), service.getDiscount());
        }
        
        appointment.setBookedServices(bookedServices);
        appointment.setAmount(totalDiscountedPrice);
        appointmentRepo.save(appointment);
                

//        Invoice invoice = new Invoice();
//        invoice.setAppointment(saved);
//        invoice.setTotal(totalPrice);
//        invoice.setCouponDiscount(totalDiscount);
//        invoice.setFinalAmount(totalDiscountedPrice);
//        invoice.setDateTime(LocalDateTime.now());
//        invoiceRepo.save(invoice);

//        AppointmentDto appointmentDto = new AppointmentDto();
//        appointmentDto.setAppointmentId(saved.getAppointmentId());
//        appointmentDto.setCustomerId(saved.getCustomer().getUserId());
//        appointmentDto.setDateTime(saved.getDateTime());
//        appointmentDto.setLocation(saved.getLocation());
//        appointmentDto.setStatus(saved.getStatus());
//        appointmentDto.setBookedServices(req.getServiceIds());
        
        
        //return saved;
        return AppointmentMapper.toDto(appointment);
    }

    @Override
    public AppointmentDto getAppointmentDetails(Long appointmentId) {

    	Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() ->
                new RuntimeException("Appointment not found"));
    	
        return AppointmentMapper.toDto(appointment);
    }
    
    @Override
    public AppointmentDto acceptAppointmentByProfessional(Long appointmentId) {

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

        return AppointmentMapper.toDto(appointmentRepo.save(appointment));
    }


    // Update appointment status
    @Override
    public AppointmentDto updateStatus(Long appointmentId,
                                    AppointmentStatus newStatus) {

        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        AppointmentStatus current = appointment.getStatus();

        // Validate transitions
        if (current == AppointmentStatus.CONFIRMED &&
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

        return AppointmentMapper.toDto(appointmentRepo.save(appointment));
    }

    // =================================================
    // 5️⃣ CUSTOMER CANCEL
    // =================================================
    @Override
    public AppointmentDto cancelAppointment(Long id) {
        return updateStatus(id, AppointmentStatus.CANCELLED);
    }

//    // =================================================
//    // 6️⃣ PAYMENT
//    // =================================================
//    @Override
//    public Payment makePayment(Long invoiceId, String method) {
//
//        Invoice invoice = invoiceRepo.findById(invoiceId)
//                .orElseThrow(() -> new RuntimeException("Invoice not found"));
//
//        Payment payment = new Payment();
//        payment.setInvoice(invoice);
//        payment.setAmount(invoice.getTotalAmount());
//        payment.setPaymentMethod(method);
//        payment.setStatus(PaymentStatus.SUCCESS);
//        //payment.setPaymentDate(LocalDateTime.now());
//
//        return paymentRepo.save(payment);
//    }
    
    @Transactional
    public void markPaymentPending(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.PAYMENT_PENDING);
    }

    @Transactional
    public void confirmAppointment(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.CONFIRMED);
    }

}
