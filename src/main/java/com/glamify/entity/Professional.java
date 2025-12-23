package com.glamify.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Professional extends User {

    private String speciality;
    private String gender;
    private int experienceInYears;
    private double rating;
}
