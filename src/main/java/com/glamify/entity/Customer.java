package com.glamify.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Customer extends User {
    private String gender;
}
