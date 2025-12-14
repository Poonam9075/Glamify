package com.dac.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "service_id"))

public class Service extends BaseEntity
{

    @Column(name = "service_name", length = 100, nullable = false)
    private String serviceName;

    @Column(length = 255)
    private String description;

    @Column(name = "estimated_time", length = 50)
    private String estimatedTime;   // e.g., "30 mins", "1 hour"

    @Column
    private float discount;

    @Column(nullable = false)
    private float price;

    @Column(length = 100)
    private String category;

}