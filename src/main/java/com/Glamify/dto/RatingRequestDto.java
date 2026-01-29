package com.Glamify.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class RatingRequestDto {

    @NotNull
    private Long appointmentId;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    // ✅ PROPER GETTERS & SETTERS

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}