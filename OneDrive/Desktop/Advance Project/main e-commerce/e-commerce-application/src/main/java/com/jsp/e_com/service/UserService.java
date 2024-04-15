package com.jsp.e_com.service;

import org.springframework.http.ResponseEntity;

import com.jsp.e_com.request.dto.UserRequest;
import com.jsp.e_com.responce.dto.UserResponce;
import com.jsp.e_com.util.ResponseStructure;

public interface UserService {

	ResponseEntity<String> registerUser(UserRequest request);

	ResponseEntity<ResponseStructure<UserResponce>> verifyOTP(String otp);

}
