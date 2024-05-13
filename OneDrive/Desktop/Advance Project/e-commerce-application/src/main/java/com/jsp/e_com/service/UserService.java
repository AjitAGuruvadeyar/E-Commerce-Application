package com.jsp.e_com.service;

import org.springframework.http.ResponseEntity;

import com.jsp.e_com.request.dto.AuthRequest;
import com.jsp.e_com.request.dto.OtpRequest;
import com.jsp.e_com.request.dto.UserRequest;
import com.jsp.e_com.responce.dto.AuthResponce;
import com.jsp.e_com.responce.dto.UserResponce;
import com.jsp.e_com.util.ResponseStructure;
import com.jsp.e_com.util.SimpleResponseStructer;

public interface UserService {

	ResponseEntity<SimpleResponseStructer> registerUser(UserRequest request);

	ResponseEntity<ResponseStructure<UserResponce>> verifyOTP(OtpRequest otpRequest);

	ResponseEntity<ResponseStructure<AuthResponce>> login(AuthRequest authRequste);

	ResponseEntity<SimpleResponseStructer> userLogout(String accessToken, String refreshToken);

	ResponseEntity<ResponseStructure<AuthResponce>> refreshLogin(String accessToken, String refreshToken);

}
