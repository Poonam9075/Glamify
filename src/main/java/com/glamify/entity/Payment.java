package com.glamify.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private String paymentMethod;
    private double amount;
    private String status;

    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}
