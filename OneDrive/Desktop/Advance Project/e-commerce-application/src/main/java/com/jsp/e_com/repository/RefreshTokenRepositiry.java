package com.jsp.e_com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.e_com.entity.AccessToken;
import com.jsp.e_com.entity.RefreshToken;
@Repository
public interface RefreshTokenRepositiry extends JpaRepository<RefreshToken, Integer>{

	boolean existsByTokenAndIsBlocked(String rt, boolean b);

	Optional<RefreshToken> findByToken(String refreshToken);

//	List<RefreshToken> findAllIsBlocked(boolean b);

}
