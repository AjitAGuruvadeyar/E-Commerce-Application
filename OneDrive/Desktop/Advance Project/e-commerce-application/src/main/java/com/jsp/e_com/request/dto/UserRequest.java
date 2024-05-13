package com.jsp.e_com.request.dto;

import org.springframework.stereotype.Component;

import com.jsp.e_com.enums.UserRole;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Component
@Setter
@Getter
public class UserRequest {
    
	@NotNull(message = "fill the username")
	private String displayName;
	@Email(regexp = "[a-z0-9]+@[a-z0-9.-]+\\.[a-z]{2,3}")
	@NotNull(message = "Invalid Email")
	private String userEmail;
	@NotBlank(message = "Password is required")
	@NotNull(message = "Password is required")
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must"
			+ " contain at least one letter, one number, one special character")
	private String password;
	private UserRole role;



}
