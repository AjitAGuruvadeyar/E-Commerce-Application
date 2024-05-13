package com.jsp.e_com.responce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponse {
	
	private int contactId;
	private String name;
	private String email;
	private Long phoneNumber;

}
