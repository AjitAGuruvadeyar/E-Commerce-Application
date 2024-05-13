package com.jsp.e_com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.e_com.request.dto.ContactRequest;
import com.jsp.e_com.responce.dto.ContactResponse;
import com.jsp.e_com.service.ContactService;
import com.jsp.e_com.util.ResponseStructure;




import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1")
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:5173/")
public class ContactController {
	private ContactService contactSrevice;
	
	
	@PostMapping("/address/addContact")
	public ResponseEntity<ResponseStructure<ContactResponse>>addContact(@RequestBody  ContactRequest contactRequest,@RequestParam int  addressId){
		String name = contactRequest.getName();
		System.out.println(name);
		return contactSrevice.addContact(contactRequest,addressId);
	}
	
	@PutMapping("/contact/{contactId}")
	public ResponseEntity<ResponseStructure<ContactResponse>> updateContact(@RequestBody  ContactRequest contactRequest,@PathVariable int contactId){
		
		return contactSrevice.updateContact(contactRequest,contactId);
	}

}