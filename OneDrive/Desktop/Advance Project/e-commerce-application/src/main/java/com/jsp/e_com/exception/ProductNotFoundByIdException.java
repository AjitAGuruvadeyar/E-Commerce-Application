package com.jsp.e_com.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductNotFoundByIdException extends RuntimeException{
	String message;

}
