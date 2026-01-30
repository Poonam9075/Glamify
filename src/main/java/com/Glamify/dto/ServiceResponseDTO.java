package com.Glamify.dto;

import java.sql.Time;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponseDTO {

    private Long id;
    private String serviceName;
    private int price;
    private Time estimatedTime;

    // category info (flattened for frontend)
    private Long categoryId;
    private String categoryName;
}
