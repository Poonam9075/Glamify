package com.glamify.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.glamify.entity.AppointmentStatus;
import lombok.Data;

@Data
public class AppointmentViewDto {

    private Long appointmentId;
    private LocalDateTime dateTime;
    private String location;
    private AppointmentStatus status;

    private String customerName;
    private String professionalName;

    private List<BeautyServiceViewDto> services;

    private int totalEstimatedTime;
}
