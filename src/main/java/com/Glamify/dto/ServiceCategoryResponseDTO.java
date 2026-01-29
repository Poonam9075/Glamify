
package com.Glamify.dto;

import com.Glamify.entities.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceCategoryResponseDTO {

    private Long id;
    private String categoryName;
    private String description;
    private Status status;
}
