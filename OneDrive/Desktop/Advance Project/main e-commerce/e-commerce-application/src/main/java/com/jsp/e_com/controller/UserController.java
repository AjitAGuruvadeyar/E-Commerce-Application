package com.jsp.e_com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.e_com.request.dto.UserRequest;
import com.jsp.e_com.responce.dto.UserResponce;
import com.jsp.e_com.service.UserService;
import com.jsp.e_com.util.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
	
	
	private UserService userService;
	
	@PostMapping("/users/registertion")
	public ResponseEntity<String> registerUser(@RequestBody UserRequest request){
		return userService.registerUser(request);

	}
	
	@PostMapping("/otp-verify")
	public ResponseEntity<ResponseStructure<UserResponce>> verifyOTP(@RequestParam String otp){
		return userService.verifyOTP(otp);
	}

}
