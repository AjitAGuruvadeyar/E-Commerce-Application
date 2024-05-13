package com.jsp.e_com.responce.dto;

import com.jsp.e_com.enums.UserRole;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Builder
@Setter
@Getter
public class AuthResponce {
	private int id;
	private String username;
	private long accessExpriration;
	private long refershExpiration;
	private UserRole role;
	

}
