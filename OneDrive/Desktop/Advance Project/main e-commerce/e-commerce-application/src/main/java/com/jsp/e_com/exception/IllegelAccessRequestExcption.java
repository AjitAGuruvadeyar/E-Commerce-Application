package com.jsp.e_com.exception;


public class IllegelAccessRequestExcption extends RuntimeException{
	private String message;

	public IllegelAccessRequestExcption(String message) {
	
		this.message = message;
	}
	

}
