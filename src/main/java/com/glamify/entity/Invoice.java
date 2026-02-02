package com.glamify.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    
    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
    
    private String invoiceNumber;

    private double baseAmount;
    private double dicountAmount;
    private double taxAmount;
    private double totalAmount;
    private LocalDateTime generatedAt;
    
    
//    private double total;
//    private double couponDiscount;
//    private double finalAmount;
//    private LocalDateTime dateTime;

    
}
