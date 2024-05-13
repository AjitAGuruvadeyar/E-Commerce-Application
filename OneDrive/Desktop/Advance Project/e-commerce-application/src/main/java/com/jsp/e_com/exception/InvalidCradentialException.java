package com.jsp.e_com.exception;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class InvalidCradentialException extends RuntimeException{
	private String massage;

}
