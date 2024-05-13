package com.jsp.e_com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jsp.e_com.repository.UserRepository;

import lombok.AllArgsConstructor;
@Service      //making it as bean class to implement itself
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return userRepository.findByUsername(username).
				map(CustomUserDetails::new).  //funtional method can take only one or no arguments
				orElseThrow(()->new UsernameNotFoundException("user not found"));
	}

}



