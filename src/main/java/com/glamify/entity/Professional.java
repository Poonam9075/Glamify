package com.glamify.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Professional extends User {

    @Column(nullable = false, length = 100)
    private String speciality;

    @Column(nullable = false)
    private int experienceInYears;

    @Column(nullable = false)
    private double rating;
    
    @Column(nullable = false)
    private boolean approved = false;
}
