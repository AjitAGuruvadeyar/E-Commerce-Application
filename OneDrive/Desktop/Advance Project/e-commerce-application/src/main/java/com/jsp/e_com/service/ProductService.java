package com.jsp.e_com.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.jsp.e_com.entity.Product;
import com.jsp.e_com.enums.OrderBy;
import com.jsp.e_com.request.dto.ProductRequest;
import com.jsp.e_com.request.dto.SearchFilter;
import com.jsp.e_com.responce.dto.ProductResponse;
import com.jsp.e_com.util.ResponseStructure;

public interface ProductService {

	ResponseEntity<ResponseStructure<ProductResponse>> addProduct(ProductRequest productRequest);

	ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(ProductRequest productRequest, int productId);

	ResponseEntity<ResponseStructure<ProductResponse>> findProducts();

	ResponseEntity<ResponseStructure<ProductResponse>> findByProductId(int productId);

	List<Product> findProductsByFilters(SearchFilter filter, Integer page,OrderBy orderBy,String sortBy);

	ResponseEntity<ResponseStructure<ProductResponse>> SearchProduct(String searchString);

}
