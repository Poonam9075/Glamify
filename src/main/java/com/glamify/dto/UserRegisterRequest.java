package com.glamify.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class UserRegisterRequest {

	@NotBlank
    private String fullName;
    
	@Email
    @NotBlank
	private String email;
	
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    private String phone;
    
    @NotBlank
    @Size(min = 6)
    private String password;
    
    private String gender;   
    
}
