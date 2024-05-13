package com.jsp.e_com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.e_com.entity.Contact;
@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
