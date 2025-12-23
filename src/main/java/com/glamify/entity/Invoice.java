package com.glamify.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;

    private double total;
    private double couponDiscount;
    private double finalAmount;
    private LocalDateTime dateTime;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
}
