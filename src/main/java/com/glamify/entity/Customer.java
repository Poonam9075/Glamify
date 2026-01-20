package com.glamify.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;

@Data
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {
    private String gender;
}
