package com.glamify.dto;

import lombok.Data;

@Data
public class BookedServiceDto {

    private Long id;
    private Long appointmentId;
    private Long beautyServiceId;
    private double priceAtBooking;
    private int estimatedTime;
}
