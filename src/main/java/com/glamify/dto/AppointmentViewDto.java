package com.glamify.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class AppointmentViewDto {

    private Long appointmentId;
    private LocalDateTime dateTime;
    private String location;
    private String customerName;
    private String professionalName;
    private List<BeautyServiceViewDto> services;
    private int totalEstimatedTime;
    private String invoiceNumber; 
    private double amount;
    private String status;
    private String paymentStatus;
}
