package com.jsp.e_com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.e_com.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
