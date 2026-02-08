package com.Glamify.dto;


import java.time.LocalDateTime;

import com.Glamify.entities.AppointmentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminAppointmentResponseDTO {

    private Long appointmentId;

    private String customerName;
    private String professionalName;
    private String serviceName;

    private LocalDateTime appointmentDateTime;
    private String serviceAddress;

    private AppointmentStatus status;
}
