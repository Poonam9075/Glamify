package com.Glamify.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "service_category_id"))
public class ServiceCategory extends BaseEntity {

    @Column(length = 50, nullable = false, unique = true)
    private String categoryName;

    @Column(length = 150)
    private String description;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Status status;   // ACTIVE / INACTIVE

}
