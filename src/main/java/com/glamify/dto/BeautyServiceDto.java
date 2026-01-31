package com.glamify.dto;

import lombok.Data;

@Data
public class BeautyServiceDto {

    private Long id;
    private String name;
    private String category;
    private double price;
    private int duration;
    private int discount;    
    private boolean active;
}
