package com.jsp.e_com.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidEmailIdException extends RuntimeException{

	private String message;
}
