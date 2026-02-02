package com.glamify.exception;

import com.glamify.entity.AppointmentStatus;

public class AppointmentStatusValidator {

    public static void validate(AppointmentStatus current,
                                AppointmentStatus next) {

        switch (current) 
        {
	        case REQUESTED -> {
	            if (next != AppointmentStatus.CONFIRMED &&
	                next != AppointmentStatus.CANCELLED) {
	                throw new InvalidAppointmentStatusException(
	                    "REQUESTED can only move to CONFIRMED or CANCELLED");
	            }
	        }
        
            case CONFIRMED -> {
                if (next != AppointmentStatus.ACCEPTED &&
                    next != AppointmentStatus.CANCELLED) {
                    throw new InvalidAppointmentStatusException(
                        "CONFIRMED can only move to ACCEPTED or CANCELLED");
                }
            }

            case ACCEPTED -> {
                if (next != AppointmentStatus.IN_PROGRESS &&
                    next != AppointmentStatus.CANCELLED) {
                    throw new InvalidAppointmentStatusException(
                        "ACCEPTED can only move to IN_PROGRESS or CANCELLED");
                }
            }

            case IN_PROGRESS -> {
                if (next != AppointmentStatus.COMPLETED) {
                    throw new InvalidAppointmentStatusException(
                        "IN_PROGRESS can only move to COMPLETED");
                }
            }

            case COMPLETED, CANCELLED -> {
                throw new InvalidAppointmentStatusException(
                    current + " is a final state and cannot be changed");
            }
        }
    }
}
