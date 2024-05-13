package com.jsp.e_com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.e_com.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	boolean existsByEmail(String userEmail);

	Optional<User> findByEmail(String username);

	Optional<User> findByUsername(String username);


}
