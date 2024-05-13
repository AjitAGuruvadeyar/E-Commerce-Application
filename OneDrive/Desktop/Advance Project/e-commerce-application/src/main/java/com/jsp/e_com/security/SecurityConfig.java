package com.jsp.e_com.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jsp.e_com.jwt.JwtFilter;
import com.jsp.e_com.jwt.JwtService;
import com.jsp.e_com.repository.AccessTokenReopsitory;
import com.jsp.e_com.repository.RefreshTokenRepositiry;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
	
	private CustomUserDetailsService userDetailService;
	private AccessTokenReopsitory accessTokenReopsitory;
	private JwtService jwtService;
	private RefreshTokenRepositiry refreshTokenRepositiry;
	

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	AuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailService);

		return provider;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		return http.authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/login","/api/v1/users/registertion","/api/v1/otp-verify","/api/v1/products/filter","/api/v1/product/{productId}").permitAll().anyRequest().authenticated())
				.csrf(csrf->csrf.disable())
				.sessionManagement(management->{
					management.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				})
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(new JwtFilter(accessTokenReopsitory, refreshTokenRepositiry, jwtService), UsernamePasswordAuthenticationFilter.class).build();
	}

	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
