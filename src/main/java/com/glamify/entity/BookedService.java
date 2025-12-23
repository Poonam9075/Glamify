package com.glamify.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BookedService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private BeautyService beautyService;

    private double priceAtBooking;
    private int estimatedTime;
}
