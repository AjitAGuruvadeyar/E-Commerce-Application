package com.jsp.e_com.jwt;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jsp.e_com.entity.AccessToken;
import com.jsp.e_com.repository.AccessTokenReopsitory;
import com.jsp.e_com.repository.RefreshTokenRepositiry;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;


public class JwtFilter extends OncePerRequestFilter {
	
	private AccessTokenReopsitory accessTokenRepository;
	private RefreshTokenRepositiry refreshTokenRepository;
	private JwtService jwtService;


	public JwtFilter(AccessTokenReopsitory accessTokenRepository, RefreshTokenRepositiry refreshTokenRepository,
			JwtService jwtService) {
		super();
		this.accessTokenRepository = accessTokenRepository;
		this.refreshTokenRepository = refreshTokenRepository;
		this.jwtService = jwtService;
	}
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		System.out.println("inside the jwtfilter");
		
		Cookie[] cookies = request.getCookies();
		 String accessToken=null;
		 String refreshToken=null;
		if(cookies!=null)
		for(Cookie cookie:cookies)
		{
			if(cookie.getName().equals("at"))accessToken =cookie.getValue();
			
			if(cookie.getName().equals("rt"))refreshToken=cookie.getValue();
		}
		
		if(accessToken!=null && refreshToken!=null )
		{
			System.out.println("at and rt are not null");
			
			if(accessTokenRepository.existsByTokenAndIsBlocked(accessToken,true)&& refreshTokenRepository.existsByTokenAndIsBlocked(refreshToken,true))
				throw new RuntimeException();
			String username = jwtService.getUsername(accessToken);
			String userRole=jwtService.getUserRole(accessToken);
			
			System.out.println(username+" "+userRole);
			
			if(username!=null && userRole!=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
				System.out.println("inside the SecurityContextHolder if condition ");
				
			    UsernamePasswordAuthenticationToken passwordAuthenticationToken = new  UsernamePasswordAuthenticationToken(username, null,
			    		Collections.singleton(new SimpleGrantedAuthority(userRole)));
				passwordAuthenticationToken
				.setDetails(new WebAuthenticationDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
			}
			
		}
		
		filterChain.doFilter(request, response);
	}





}