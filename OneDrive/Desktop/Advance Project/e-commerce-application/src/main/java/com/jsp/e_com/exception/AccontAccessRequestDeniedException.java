package com.jsp.e_com.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccontAccessRequestDeniedException extends RuntimeException{
	String message;

}
