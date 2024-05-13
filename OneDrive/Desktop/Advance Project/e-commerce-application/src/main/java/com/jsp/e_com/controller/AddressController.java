package com.jsp.e_com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.e_com.request.dto.AddressRequest;
import com.jsp.e_com.responce.dto.AddressResponce;
import com.jsp.e_com.service.AddressService;
import com.jsp.e_com.util.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@RequestMapping("api/v1")
@RestController
@CrossOrigin(allowCredentials = "true",origins="http://localhost:5173/")
public class AddressController {
	private AddressService addressService;
	
	@PostMapping("/addAddress")
	private ResponseEntity<ResponseStructure<AddressResponce>> addAddress(@RequestBody @Valid AddressRequest addressRequest, 
			
			@CookieValue(name = "at",required = false)String accessToken){
		return addressService.addAddress(addressRequest,accessToken);
	}

	@GetMapping("/findAddress")
	public ResponseEntity<?>findAddressByUser(@CookieValue(name = "at",required = false) String accessToken){
		return addressService.findAddressByUser(accessToken);
	}
	

	@PutMapping("/address/{addressId}")
	private ResponseEntity<ResponseStructure<AddressResponce>> updateAddress(@RequestBody AddressRequest addressRequest, @PathVariable int addressId){
		return addressService.updateAddress(addressRequest,addressId);
	}
	
}
