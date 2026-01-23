
package com.Glamify.dto;

import java.sql.Time;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceCreateDTO {

    private String serviceName;
    private int price;
    private Time estimatedTime;
    private Long categoryId;   // FK
}
