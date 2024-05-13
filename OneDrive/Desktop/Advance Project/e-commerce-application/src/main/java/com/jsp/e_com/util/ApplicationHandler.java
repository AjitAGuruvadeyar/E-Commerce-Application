package com.jsp.e_com.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jsp.e_com.exception.IllegelAccessRequestExcption;
import com.jsp.e_com.exception.InvalidEmailIdException;
import com.jsp.e_com.exception.InvalidOTPException;
import com.jsp.e_com.exception.OtpExpiredException;
import com.jsp.e_com.exception.RegistretionSessionExpierdException;
import com.jsp.e_com.exception.UserAreadyPresentException;

import lombok.AllArgsConstructor;
@RestControllerAdvice
@AllArgsConstructor
public class ApplicationHandler {
	
private ErrorStructure<String> errorStructure;
	
	public ResponseEntity<ErrorStructure<String>> errorResponse(HttpStatus code,String message,String rootCause){
		return ResponseEntity.ok(errorStructure.setErrorStatuscode(code.value()).setErrorMessage(message).setRootCause(rootCause));
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handeleUserAlreadyExistByEmail(UserAreadyPresentException excption){
		return errorResponse(HttpStatus.BAD_REQUEST,excption.getMessage() , "user Email is already exist in database");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handeleInvalidUserRoleSpecfid(IllegelAccessRequestExcption excption){
		return errorResponse(HttpStatus.BAD_REQUEST,excption.getMessage() , "UserRole is not specficed correctly");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handeleOTPInvalidException(InvalidOTPException excption){
		return errorResponse(HttpStatus.BAD_REQUEST,excption.getMessage() , "enter the valid otp");
	}


	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handeleOtpExpiredException(OtpExpiredException excption){
		return errorResponse(HttpStatus.BAD_REQUEST,excption.getMessage() , "otp expired");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handeleRegistretionSessionExpierdException(RegistretionSessionExpierdException excption){
		return errorResponse(HttpStatus.BAD_REQUEST,excption.getMessage() , "user registretion time is over ");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handeleInvalidEmailIdException(InvalidEmailIdException excption){
		return errorResponse(HttpStatus.BAD_REQUEST,excption.getMessage() , "user registretion time is over ");
	}
	
}
