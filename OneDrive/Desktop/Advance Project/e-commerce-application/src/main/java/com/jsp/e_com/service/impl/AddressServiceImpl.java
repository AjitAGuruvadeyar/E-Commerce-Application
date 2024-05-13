package com.jsp.e_com.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jsp.e_com.entity.Address;
import com.jsp.e_com.entity.Contact;
import com.jsp.e_com.entity.Customer;
import com.jsp.e_com.entity.Seller;
import com.jsp.e_com.enums.UserRole;
import com.jsp.e_com.exception.AddressNotFoundByIdException;
import com.jsp.e_com.exception.AddressNotFoundByUser;
import com.jsp.e_com.exception.ContactNotFoundByUser;
import com.jsp.e_com.jwt.JwtService;
import com.jsp.e_com.repository.AddressRepository;
import com.jsp.e_com.repository.ContactRepository;
import com.jsp.e_com.repository.UserRepository;
import com.jsp.e_com.request.dto.AddressRequest;
import com.jsp.e_com.responce.dto.AddressResponce;
import com.jsp.e_com.responce.dto.ContactResponse;
import com.jsp.e_com.service.AddressService;
import com.jsp.e_com.util.ResponseStructure;

import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
	private JwtService jwtService;
	private UserRepository userRepostiory;
	private AddressRepository addressRepository;
	private ContactRepository contactRepository;
	private ResponseStructure<AddressResponce> responseStructure;



	@Override
	public ResponseEntity<ResponseStructure<AddressResponce>> addAddress(@Valid AddressRequest addressRequest,
			String accesstoken) {
//		System.out.println("hello iam ajit");
		String username = jwtService.getUsername(accesstoken);
//		System.out.println("hello iam AG");
		System.out.println();
		return  userRepostiory.findByUsername(username).map(user->{
			System.out.println(user.getUserRole());
			System.out.println(addressRequest.getPincode()+addressRequest.getCity());
			Address address = mapToAddress(addressRequest, new Address()); // Instantiate address here
			
			if (user.getUserRole().equals(UserRole.SELLER)) {
                if (((Seller) user).getAddress()==null) {
//                	System.out.println("aaaaaa");
                    address = addressRepository.save(address); // Save address here
                    ((Seller) user).setAddress(address);
                    userRepostiory.save(user);
                }
            } else if (user.getUserRole() == UserRole.CUSTOMER) {
                if (((Customer) user).getAddress().size() < 5) {
                    address = addressRepository.save(address); // Save address here
                    ((Customer) user).getAddress().add(address);
                    userRepostiory.save(user);
                }
            }
			
			return ResponseEntity.ok().body(responseStructure.setStatuscode(HttpStatus.OK.value())
					.setMessage("the address added successfully")
					.setData(mapToAddressResponse(address)));

		}).orElseThrow(()->new UsernameNotFoundException("user is not found with the username "+username));

	}
	
	private AddressResponce mapToAddressResponse(Address address) {
		List<ContactResponse> mapToContactResponses =null;
		if(address.getContacts()!=null) {
			mapToContactResponses= mapToContactResponses(address.getContacts());
		}

		return AddressResponce.builder().addressId(address.getAddressId())
				.streetAddress(address.getStreetAddress())
				.streetAddressAdditional(address.getStreetAddressAdditional())
				.city(address.getCity())
				.state(address.getState())
				.pincode(address.getPincode())
				.addressType(address.getAddressType())
				. contacts( mapToContactResponses )
				.build();

	}
	private List<ContactResponse> mapToContactResponses(List<Contact> contacts) {
		List<ContactResponse> contactResponses=new ArrayList<>();

		for(Contact contact:contacts)
		{
			contactResponses.add(mapToContactResponse(contact));
		}
		return contactResponses;
	}
	private ContactResponse mapToContactResponse(Contact contact) {
		return ContactResponse.builder()
				.contactId(contact.getContactId())
				.name(contact.getName())
				.email(contact.getEmail())
				.phoneNumber(contact.getPhoneNumber()).build();
	}
	private Address mapToAddress(AddressRequest addressRequest, Address address) {
		address.setStreetAddress(addressRequest.getStreetAddress());
		address.setStreetAddressAdditional(addressRequest.getStreetAddressAdditional());
		address.setCity(addressRequest.getCity());
		address.setState(addressRequest.getState());
		address.setAddressType(addressRequest.getAddressType());
		address.setPincode(addressRequest.getPincode());
		return address;
	}
	
	@Override
	public ResponseEntity<?> findAddressByUser(String accessToken) {

		return userRepostiory.findByUsername("ajitguru467@gmail.com").map(user -> {
			List<Address> addresses=null;
			Address address=null;
			List<Contact> contacts=null;
			if(user.getUserRole()==UserRole.SELLER) {
				address=((Seller)user).getAddress();
				if(address==null) throw new AddressNotFoundByUser("The User don't have address");
				return ResponseEntity.ok().body(responseStructure.setData(mapToAddressResponse(address)));
			}
			if(user.getUserRole()==UserRole.CUSTOMER) {
				addresses=((Customer)user).getAddress();
				if(addresses==null)throw new AddressNotFoundByUser("The User dont have Address");
				return ResponseEntity.ok().body(responseStructure.setLists(mapToAddressResponseList(addresses)));
			}
			return null;
			
	
		}
		).orElseThrow(()->new UsernameNotFoundException("user is not found"));
	}

	private List<AddressResponce> mapToAddressResponseList(List<Address> addresses)
	{

		List<AddressResponce> addressResponses=new ArrayList<>();
		for(Address address: addresses)
		{
			addressResponses.add(mapToAddressResponse(address));
		}
		return addressResponses;
	}
	
	
	@Override
	public ResponseEntity<ResponseStructure<AddressResponce>> updateAddress(AddressRequest addressRequest,
			int addressId) {
		Address address2=addressRepository.findById(addressId).map(address -> {
			return addressRepository.save(mapToAddress(address, addressRequest));
		}).orElseThrow(()-> new AddressNotFoundByIdException("address not found by id"));
		return ResponseEntity.ok(new ResponseStructure<AddressResponce>()
				.setData(mapToAddressResponse(address2))
				.setStatuscode(HttpStatus.OK.value())
				.setMessage("address updated"));
	}
	private Address mapToAddress(Address address,AddressRequest addressRequest) {
		address.setAddressType(addressRequest.getAddressType());
		address.setCity(addressRequest.getCity());
		address.setPincode(addressRequest.getPincode());
		address.setState(addressRequest.getState());
		address.setStreetAddress(addressRequest.getStreetAddress());
		address.setStreetAddressAdditional(addressRequest.getStreetAddressAdditional());
		return address;
	}

}


