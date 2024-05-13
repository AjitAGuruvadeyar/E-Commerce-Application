package com.jsp.e_com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.e_com.entity.Address;
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{

}
