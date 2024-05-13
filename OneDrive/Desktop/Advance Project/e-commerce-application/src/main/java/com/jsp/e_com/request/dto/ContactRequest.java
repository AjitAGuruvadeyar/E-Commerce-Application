package com.jsp.e_com.request.dto;

import com.jsp.e_com.enums.ContactPriority;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
@Getter
public class ContactRequest {
	@NotBlank
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only alphabets and spaces")
    private String name;

    @NotBlank
    @Pattern(regexp = "^\\d+$", message = "Phone number must be numeric")
    private long phoneNumber;

    @NotBlank
    @Email(regexp = "^.+@gmail\\.com$"
    		+ "", message = "Email must end with @gmail.com")
    private String email;

    @NotNull(message = "Priority is required")
    private ContactPriority priority;

}
