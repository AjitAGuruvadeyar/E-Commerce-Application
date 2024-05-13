package com.jsp.e_com.util;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class SimpleResponseStructer {
	private String massage;
	private int status;
	
	public SimpleResponseStructer setMassage(String massage) {
		this.massage = massage;
		return this;
	}
	
	public SimpleResponseStructer setStatus(int status) {
		this.status = status;
		return this;

	}
	

}
