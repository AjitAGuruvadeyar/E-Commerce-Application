package com.jsp.e_com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.e_com.entity.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Integer>{

}
