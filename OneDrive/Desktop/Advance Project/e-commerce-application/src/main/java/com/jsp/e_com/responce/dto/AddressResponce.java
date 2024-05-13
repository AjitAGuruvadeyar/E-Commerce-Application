package com.jsp.e_com.responce.dto;

import java.util.List;

import com.jsp.e_com.enums.AddressType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Builder
public class AddressResponce {
	
	private int addressId;
	private String streetAddress;
	private String streetAddressAdditional;
	private String city;
	 private String state;
	 private int pincode;
	 private AddressType addressType;
	 private List<ContactResponse> contacts;


}
