package com.jsp.e_com.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidOTPException extends RuntimeException {

	private String message;


	
	
}
