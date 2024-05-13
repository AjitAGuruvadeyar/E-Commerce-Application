package com.jsp.e_com.request.dto;

import org.springframework.stereotype.Component;

import com.jsp.e_com.enums.AvailabilityStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
public class SearchFilter {
	private int minPrice;
	private int maxPrice;
	private String category;
	private AvailabilityStatus availability;
	private int discount;

}
