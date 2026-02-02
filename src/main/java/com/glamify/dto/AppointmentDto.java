package com.glamify.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class AppointmentDto {

    private Long appointmentId;
    private LocalDateTime dateTime;
    private String location;    
    private Long customerId;
    private Long professionalId;
    private List<Long> bookedServiceIds;
    private double amount;
    private String  status;
    private String paymentStatus;
}
