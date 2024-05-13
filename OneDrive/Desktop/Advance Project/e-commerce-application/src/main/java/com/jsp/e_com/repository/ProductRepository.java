package com.jsp.e_com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.http.ResponseEntity;

import com.jsp.e_com.entity.Product;
import com.jsp.e_com.responce.dto.ProductResponse;
import com.jsp.e_com.util.ResponseStructure;

public interface ProductRepository extends JpaRepository<Product, Integer>,JpaSpecificationExecutor<Product>{
	

	List<Product> findByProductNameIgnoreCaseContainingOrProductDescriptionIgnoreCaseContaining(String title,String description);
}
