package com.jsp.e_com.responce.dto;

import java.util.List;

import com.jsp.e_com.enums.AvailabilityStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ProductResponse {
	
	private int productId;
	private String productName;
	private String productDescription;
	private double productPrice;
	private int productQuantity;
	private String category;
	private AvailabilityStatus availabilityStatus;
	private List<ImageResponse> images; 

}
