package com.Glamify.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentCreateDTO {

    private Long professionalId;   // selected professional
    private Long serviceId;        // selected service

    private LocalDateTime appointmentDateTime;

    private String serviceAddress;
}