package com.jsp.e_com.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ContactNotFoundByUser extends RuntimeException{
	
	String message;

}
