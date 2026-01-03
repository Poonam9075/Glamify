package com.glamify.dto;

import java.util.List;
import lombok.Data;

@Data
public class CustomerBookingRequest {
    private String location;
    private String dateTime;
    private List<Long> serviceIds;
}
