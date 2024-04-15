package com.jsp.e_com.service.impl;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.e_com.cache.CacheStore;
import com.jsp.e_com.entity.Customer;
import com.jsp.e_com.entity.Seller;
import com.jsp.e_com.entity.User;
import com.jsp.e_com.enums.UserRole;
import com.jsp.e_com.exception.IllegelAccessRequestExcption;
import com.jsp.e_com.exception.InvalidOTPException;
import com.jsp.e_com.exception.UserAreadyPresentException;
import com.jsp.e_com.repository.CustomerRepository;
import com.jsp.e_com.repository.SellerRepository;
import com.jsp.e_com.repository.UserRepository;
import com.jsp.e_com.request.dto.UserRequest;
import com.jsp.e_com.responce.dto.UserResponce;
import com.jsp.e_com.service.UserService;
import com.jsp.e_com.util.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	
	private CustomerRepository customerRepository;
	private SellerRepository sellerRepository;
	private UserRepository userRepository;
	private ResponseStructure<UserResponce> responseStructure;
    private CacheStore<String> otpCache;
    private CacheStore<User> userCache;

	
	@Override
	public ResponseEntity<String> registerUser(UserRequest userRequest) {
		
		if(userRepository.existsByEmail(userRequest.getUserEmail()))
			throw new UserAreadyPresentException("User already exist");
		
		User user=mapToChildEntity(userRequest);
		String otp=generateOTP();
		
		otpCache.add("otp", otp);
		userCache.add("user", user);
		
		
		
		return ResponseEntity.ok(otp);
		
	}


	@Override
	public ResponseEntity<ResponseStructure<UserResponce>> verifyOTP(String otp) {
       if(!otpCache.get("otp").equals(otp)) throw new InvalidOTPException("otp not matched");
       
       User user=userCache.get("user");
       user.setEmailVerified(true);
       
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(responseStructure.setData(mapToUserResponce(user))
						.setMessage("otp genaretet sucsssefully")
						.setStatuscode(HttpStatus.OK.value()));
	}
	
	
	
	
	private String generateOTP() {
		return String.valueOf(new Random().nextInt(100000,999999));
	
	}



	private <T extends User> T mapToChildEntity(UserRequest userRequest) {
		
		UserRole role=userRequest.getRole();
		
		User user=null;
		
		switch (role) {
		case SELLER ->user=new Seller();
		case CUSTOMER ->user=new Customer();
		default ->throw new IllegelAccessRequestExcption("In valid user role");
		}
		
		user.setDisplayName(userRequest.getDisplayName());
		user.setUsername(userRequest.getUserEmail().split("@gmail.com")[0]);
		user.setEmail(userRequest.getUserEmail());
		user.setPassword(userRequest.getPassword());
		user.setEmailVerified(false);
		user.setUserRole(userRequest.getRole());
		return (T) user;
	}

	static UserResponce mapToUserResponce(User user) {
           UserResponce responce=UserResponce.builder()
        		   .displayName(user.getDisplayName())
        		   .userId(user.getUserId())
        		   .userName(user.getUsername())
        		   .email(user.getEmail())
        		   .isEmailVerified(user.isEmailVerified())
        		   .role(user.getUserRole())
        		   .build();
           return responce;
		
	}
//
//
//	static Seller mapToSellerEntity(UserRequest request, Seller seller) {
//		String email=request.getUserEmail();
//		seller.setEmail(email);
//		seller.setDeleted(false);
//		seller.setEmailVerified(false);
//		seller.setDisplayName(request.getDisplayName());
//		seller.setPassword(request.getPassword());
//		seller.setUserRole(request.getRole());
//		seller.setUsername(email.substring(0, email.indexOf('@')));
//		return seller;
//	}
//
//	static Customer mapToCustomerEntity(UserRequest request, Customer customer) {
//		String email=request.getUserEmail();
//		customer.setEmail(email);
//		customer.setDeleted(false);
//		customer.setEmailVerified(false);
//		customer.setDisplayName(request.getDisplayName());
//		customer.setPassword(request.getPassword());
//		customer.setUserRole(request.getRole());
//		customer.setUsername(email.substring(0, email.indexOf('@')));
//		return customer;
//	}
	
}
