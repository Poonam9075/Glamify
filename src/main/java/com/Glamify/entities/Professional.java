package com.Glamify.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
@Entity
@AttributeOverride(name = "id", column = @Column(name = "professional_id"))
public class Professional extends BaseEntity {

    @Column(length = 30)
    private String speciality;

    private int experienceInYears;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProfessionalStatus status = ProfessionalStatus.PENDING;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

