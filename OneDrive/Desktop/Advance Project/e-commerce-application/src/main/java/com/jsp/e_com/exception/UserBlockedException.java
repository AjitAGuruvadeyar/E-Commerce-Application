package com.jsp.e_com.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserBlockedException extends RuntimeException {
	String message;

}
