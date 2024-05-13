package com.jsp.e_com.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.e_com.jwt.JwtService;
import com.jsp.e_com.request.dto.AuthRequest;
import com.jsp.e_com.request.dto.OtpRequest;
import com.jsp.e_com.request.dto.UserRequest;
import com.jsp.e_com.responce.dto.AuthResponce;
import com.jsp.e_com.responce.dto.UserResponce;
import com.jsp.e_com.service.UserService;
import com.jsp.e_com.util.ResponseStructure;
import com.jsp.e_com.util.SimpleResponseStructer;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(allowCredentials = "true" , origins =  "http://localhost:5173")
public class UserController {
	
	
	private UserService userService;
	private JwtService jwtService;
	
	@PostMapping("/users/registertion")
	public ResponseEntity<SimpleResponseStructer> registerUser(@RequestBody UserRequest request){
		return userService.registerUser(request);

	}
	
	@PostMapping("/otp-verify")
	public ResponseEntity<ResponseStructure<UserResponce>> verifyOTP(@RequestBody OtpRequest otpRequste){
		return userService.verifyOTP(otpRequste);
	}

	
	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<AuthResponce>> login(@RequestBody AuthRequest authRequste){
		return userService.login(authRequste);
	} 
	
	@PostMapping("/logout")
	public ResponseEntity<SimpleResponseStructer> userLogout(@CookieValue(name = "rt",required = false)String refreshToken
			,@CookieValue(name="at", required = false)String accessToken){
		return userService.userLogout(accessToken,refreshToken);

	}

	@PostMapping("/login/refresh")
	public ResponseEntity<ResponseStructure<AuthResponce>> refreshLogin(@CookieValue(name = "at",required = false)String accesToken,
			@CookieValue(name = "rt",required = false)String refreshToken)
	{
		return userService.refreshLogin(accesToken,refreshToken);
	}
	
	
	
	
}
