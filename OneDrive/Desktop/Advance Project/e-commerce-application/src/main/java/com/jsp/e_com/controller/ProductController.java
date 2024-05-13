package com.jsp.e_com.controller;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.e_com.entity.Product;
import com.jsp.e_com.enums.OrderBy;
import com.jsp.e_com.jwt.JwtService;
import com.jsp.e_com.repository.ProductRepository;
import com.jsp.e_com.request.dto.ProductRequest;
import com.jsp.e_com.request.dto.SearchFilter;
import com.jsp.e_com.responce.dto.ProductResponse;
import com.jsp.e_com.service.ProductService;
import com.jsp.e_com.service.UserService;
import com.jsp.e_com.util.ProductSpecification;
import com.jsp.e_com.util.ResponseStructure;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(allowCredentials = "true" , origins =  "http://localhost:5173")
public class ProductController {
	
	private ProductService productService;
	private SearchFilter searchFilter;
	private ProductSpecification productSpecification;
	private ProductRepository productRepository;
	
	@PreAuthorize("hasAuthority('SELLER')")
	@PostMapping("/products")
	public ResponseEntity<ResponseStructure<ProductResponse>> addProduct(@RequestBody ProductRequest productRequest){
		return productService.addProduct(productRequest);
	}
	
	@PreAuthorize("hasAuthority('SELLER')")
	@PutMapping("/products/{productId}")
	public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(@RequestBody ProductRequest productRequest,@PathVariable int productId){
		return productService.updateProduct(productRequest,productId);
	}
	
//	@GetMapping("/products")
//	public ResponseEntity<ResponseStructure<ProductResponse>> findProducts(){
//		return productService.findProducts();
//	}
	
	@GetMapping("/products/{productId}")
	public ResponseEntity<ResponseStructure<ProductResponse>> findByProductId(@PathVariable int productId){
		return productService.findByProductId(productId);
	}
	
	@GetMapping("/products/filter")
	public List<Product> findProductsByFilters(SearchFilter filter,Integer page,@RequestParam OrderBy orderBy,@RequestParam String sortBy) {
		
//		Specification<Product> specification = productSpecification.buildSpecification(filter);
//		System.out.println(specification.toString());
//		List<Product> list = productRepository.findAll(specification);
		
		return productService.findProductsByFilters(filter,page,orderBy,sortBy);
		
	}
	
	@GetMapping("/product")
	public ResponseEntity<ResponseStructure<ProductResponse>> SearchProduct(@RequestParam String searchString) {
		
		return productService.SearchProduct(searchString);
	}
	
	

}
