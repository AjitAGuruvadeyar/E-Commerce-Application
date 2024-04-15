package com.jsp.e_com.exception;

public class UserAreadyPresentException extends RuntimeException{
	
	private String message;

	public UserAreadyPresentException(String message) {
		this.message = message;
	}

	
}
