package com.jsp.e_com.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.e_com.entity.Contact;
import com.jsp.e_com.exception.AddressNotFoundByIdException;
import com.jsp.e_com.exception.AddressNotFoundByUser;
import com.jsp.e_com.exception.ContactLimitOverFlowException;
import com.jsp.e_com.exception.ContactNotFoundByIdException;
import com.jsp.e_com.repository.AddressRepository;
import com.jsp.e_com.repository.ContactRepository;
import com.jsp.e_com.request.dto.ContactRequest;
import com.jsp.e_com.responce.dto.ContactResponse;
import com.jsp.e_com.service.ContactService;
import com.jsp.e_com.util.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService{
	private AddressRepository addressRepository;
	private ContactRepository contactRepository;
	private ResponseStructure<ContactResponse> responseStructure;

	@Override
	public ResponseEntity<ResponseStructure<ContactResponse>> addContact(ContactRequest contactRequest, int addressId) {
		return addressRepository.findById(addressId).map(address -> {
			Contact contact =maptoContact(contactRequest);
			if(address.getContacts().size()>2)throw new ContactLimitOverFlowException("The contcat limit is reached");
			address.getContacts().add(contact);
			contactRepository.save(contact);
			addressRepository.save(address);
			return ResponseEntity.ok().body(responseStructure.setMessage("ram")
					.setStatuscode(HttpStatus.CREATED.value())
					.setData(maptoContactResponse(contact)));
				
		}).orElseThrow(()->new AddressNotFoundByIdException("Address is Not found"));

	}


	private Contact maptoContact( ContactRequest contactRequest) {
		Contact contact=new Contact();
		System.out.println(contactRequest.getName());
		contact.setName(contactRequest.getName());
		contact.setEmail(contactRequest.getEmail());
		contact.setPhoneNumber(contactRequest.getPhoneNumber());
		contact.setPriority(contactRequest.getPriority());
		return contact;
	}
	private ContactResponse maptoContactResponse(Contact contact) {
		return ContactResponse.builder()
				.contactId(contact.getContactId())
				.name(contact.getName())
				.email(contact.getEmail())
				.phoneNumber(contact.getPhoneNumber()).build();
	}


	@Override
	public ResponseEntity<ResponseStructure<ContactResponse>> updateContact(ContactRequest contactRequest,
			int contactId) {
		
		Contact contact2=contactRepository.findById(contactId).map(contact -> {
			return contactRepository.save(mapToContact(contact,contactRequest));
		}).orElseThrow(() -> new ContactNotFoundByIdException("contact not found by id"));
		return ResponseEntity.ok(new ResponseStructure<ContactResponse>().setData(maptoContactResponse(contact2))
				.setMessage("update contact").setStatuscode(HttpStatus.OK.value()));
	}
	
	private Contact mapToContact(Contact contact,ContactRequest request) {
		contact.setEmail(request.getEmail());
		contact.setName(request.getName());
		contact.setPhoneNumber(request.getPhoneNumber());
		contact.setPriority(request.getPriority());
		return contact;
	}


}
