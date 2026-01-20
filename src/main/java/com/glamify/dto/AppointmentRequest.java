package com.glamify.dto;

import com.glamify.entity.Appointment;
import com.glamify.entity.BookedService;
import lombok.Data;
import java.util.List;

@Data
public class AppointmentRequest {

    private Appointment appointment;     // contains dateTime, location, professional
    private List<BookedService> bookedServices;
}
