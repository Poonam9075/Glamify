package com.glamify.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.glamify.entity.AppointmentStatus;
import lombok.Data;

@Data
public class AppointmentDto {

    private Long appointmentId;
    private LocalDateTime dateTime;
    private String location;
    private AppointmentStatus  status;
    private Long customerId;
    private Long professionalId;
    private List<Long> bookedServiceIds;
}
