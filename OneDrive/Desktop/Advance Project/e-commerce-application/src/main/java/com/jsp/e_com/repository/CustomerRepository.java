package com.jsp.e_com.repository;

import java.lang.foreign.Linker.Option;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.e_com.entity.Customer;
import com.jsp.e_com.entity.User;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	Optional<Customer> findByUsername(String username);

}
