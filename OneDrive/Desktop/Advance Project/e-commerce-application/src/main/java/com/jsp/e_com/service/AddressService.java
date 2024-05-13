package com.jsp.e_com.service;

import org.springframework.http.ResponseEntity;

import com.jsp.e_com.request.dto.AddressRequest;
import com.jsp.e_com.responce.dto.AddressResponce;
import com.jsp.e_com.util.ResponseStructure;

import jakarta.validation.Valid;

public interface AddressService {

	ResponseEntity<ResponseStructure<AddressResponce>> addAddress(@Valid AddressRequest addressRequest,
			String accessToken);

	ResponseEntity<?> findAddressByUser(String accessToken);

	ResponseEntity<ResponseStructure<AddressResponce>> updateAddress(AddressRequest addressRequest, int addressId);

}
