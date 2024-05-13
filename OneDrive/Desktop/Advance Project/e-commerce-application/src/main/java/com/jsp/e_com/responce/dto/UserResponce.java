package com.jsp.e_com.responce.dto;

import com.jsp.e_com.enums.UserRole;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Builder
@Setter
@Getter
public class UserResponce {
	
	private int userId;
	private String displayName;
	private String userName;
	private String email;
	private UserRole role;
	private boolean isEmailVerified;
	

}
