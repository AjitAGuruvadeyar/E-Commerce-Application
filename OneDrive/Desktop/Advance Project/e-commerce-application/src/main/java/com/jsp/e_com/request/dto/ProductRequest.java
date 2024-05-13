package com.jsp.e_com.request.dto;

import org.springframework.stereotype.Component;

import com.jsp.e_com.enums.AvailabilityStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Component
@Setter
@Getter
public class ProductRequest {
	
	@NotNull(message = "product name is requred")
	@NotBlank(message = "product name is required")
	private String productName;
	private String productDescription;
	@NotNull(message = "product price is requred")
	@NotBlank(message = "product price is required")
	private double productPrice;
	private int productQuantity;
	private String category;
	private AvailabilityStatus availabilityStatus;

}
