package com.Glamify.services;

import com.Glamify.dto.ApiResponse;
import com.Glamify.dto.AppointmentCreateDTO;

public interface AppointmentService {

    ApiResponse bookAppointment(AppointmentCreateDTO request);
}
