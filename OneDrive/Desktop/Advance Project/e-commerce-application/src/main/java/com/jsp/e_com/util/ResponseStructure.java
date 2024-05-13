package com.jsp.e_com.util;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Component
@Getter
public class ResponseStructure<T> {
	private int statuscode;
	private String message;
	private T data;
	private List<T> lists;
	
	public ResponseStructure<T> setStatuscode(int statuscode) {
		this.statuscode = statuscode;
		return this;
	}
	public ResponseStructure<T> setMessage(String message) {
		this.message = message;
		return this;
	}
	public ResponseStructure<T> setData(T data) {
		this.data = data;
		return this;
	}
	public ResponseStructure<T> setLists(List<T> lists) {
		this.lists = lists;
		return this;
	}
	
	
	
	

}
