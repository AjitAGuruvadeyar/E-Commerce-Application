package com.jsp.e_com.exception;

public class RegistretionSessionExpierdException extends RuntimeException{
	private String message;

	public RegistretionSessionExpierdException(String message) {
		this.message = message;
	}
	
	

}
