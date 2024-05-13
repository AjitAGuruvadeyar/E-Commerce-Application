package com.jsp.e_com.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddressNotFoundByUser extends RuntimeException{

	String message;
}
