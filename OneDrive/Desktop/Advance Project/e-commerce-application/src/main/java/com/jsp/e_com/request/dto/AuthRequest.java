package com.jsp.e_com.request.dto;

import org.springframework.stereotype.Component;

import com.jsp.e_com.enums.UserRole;

import lombok.Getter;
import lombok.Setter;

@Component
@Setter
@Getter
public class AuthRequest {
	private String username;
	private String password;
	

}
