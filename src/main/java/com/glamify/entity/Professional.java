package com.glamify.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;

@Data
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Professional extends User {

    private String speciality;
    private String gender;
    private int experienceInYears;
    private double rating;
}
