package com.jsp.e_com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.e_com.entity.AccessToken;
import com.jsp.e_com.entity.User;

@Repository
public interface AccessTokenReopsitory extends JpaRepository<AccessToken, Integer>{

	boolean existsByTokenAndIsBlocked(String at, boolean b);

	Optional<AccessToken> findByToken(String refreshToken);

	List<AccessToken> findByIsBlocked(boolean b);

}
