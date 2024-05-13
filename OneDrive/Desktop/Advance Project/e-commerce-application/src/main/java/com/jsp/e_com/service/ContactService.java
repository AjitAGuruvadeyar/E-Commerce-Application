package com.jsp.e_com.service;

import org.springframework.http.ResponseEntity;

import com.jsp.e_com.request.dto.ContactRequest;
import com.jsp.e_com.responce.dto.ContactResponse;
import com.jsp.e_com.util.ResponseStructure;

public interface ContactService {

	ResponseEntity<ResponseStructure<ContactResponse>> addContact(ContactRequest contactRequest, int addressId);

	ResponseEntity<ResponseStructure<ContactResponse>> updateContact(ContactRequest contactRequest, int contactId);

}
