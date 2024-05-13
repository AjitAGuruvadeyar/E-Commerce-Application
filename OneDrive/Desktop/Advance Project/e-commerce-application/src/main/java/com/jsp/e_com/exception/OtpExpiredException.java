package com.jsp.e_com.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public class OtpExpiredException extends RuntimeException{
	
	private String message;

	public OtpExpiredException(String message) {
		this.message = message;
	}
	

}
