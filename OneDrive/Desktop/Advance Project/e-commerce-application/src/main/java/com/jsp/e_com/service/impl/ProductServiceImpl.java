package com.jsp.e_com.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jsp.e_com.entity.Product;
import com.jsp.e_com.enums.OrderBy;
import com.jsp.e_com.exception.ProductNotFoundByIdException;
import com.jsp.e_com.repository.ProductRepository;
import com.jsp.e_com.request.dto.ProductRequest;
import com.jsp.e_com.request.dto.SearchFilter;
import com.jsp.e_com.responce.dto.ProductResponse;
import com.jsp.e_com.service.ProductService;
import com.jsp.e_com.util.ProductSpecification;
import com.jsp.e_com.util.ResponseStructure;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{
	
	private ProductRepository productRepository;
	private ResponseStructure<ProductResponse> response;
	private ProductSpecification productSpecification;

	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> addProduct(ProductRequest productRequest) {
		
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		System.err.println(name);
		Product saveProduct=productRepository.save(mapToProduct(productRequest));
		return ResponseEntity.ok(response.setStatuscode(HttpStatus.CREATED.value())
				.setMessage("product added successfully")
				.setData(mapToProductResponse(saveProduct)));
	}

	private ProductResponse mapToProductResponse(Product saveProduct) {
		return ProductResponse.builder()
				.productId(saveProduct.getProductId())
				.productName(saveProduct.getProductName())
				.productDescription(saveProduct.getProductDescription())
				.productPrice(saveProduct.getProductPrice())
				.productQuantity(saveProduct.getProductQuantity())
				.category(saveProduct.getCategory())
				.availabilityStatus(saveProduct.getAvailabilityStatus())
//				.images(saveProduct)
				.build();
		
		
	}

	private Product mapToProduct(ProductRequest productRequest) {
		return Product.builder()
				.productName(productRequest.getProductName())
				.productDescription(productRequest.getProductDescription())
				.productPrice(productRequest.getProductPrice())
				.productQuantity(productRequest.getProductQuantity())
				.category(productRequest.getCategory())
				.availabilityStatus(productRequest.getAvailabilityStatus())
				.build();
	}

	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(ProductRequest productRequest,
			int productId) {

		return productRepository.findById(productId).map(existingProduct -> {
			Product updatedProduct=mapToProduct(productRequest);
			updatedProduct.setProductId(productId);
			productRepository.save(updatedProduct);
			
			return ResponseEntity.ok(response
					.setMessage("Product updated succsfully")
					.setStatuscode(HttpStatus.OK.value())
					.setData(mapToProductResponse(updatedProduct)));
		}).orElseThrow(()->new ProductNotFoundByIdException("Product is not present by this id"));
	}

	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> findProducts() {
        List<Product> products = productRepository.findAll(); 
        List<ProductResponse> productResponses = convertToProductResponses(products);
		return null;
        
//       return ResponseEntity.ok(response.setStatuscode(HttpStatus.CREATED.value())
//				.setMessage("product added successfully")
//				.setData(mapToProductResponse(productResponses)));;
        
    }

    private List<ProductResponse> convertToProductResponses(List<Product> products) {
        List<ProductResponse> responses = new ArrayList<>();
        for (Product product : products) {
            responses.add(mapToProductResponse(product));
        }
        return responses;
	}

	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> findByProductId(int productId) {

		return productRepository.findById(productId).map(Product -> {
		
			return ResponseEntity.ok(response
					.setStatuscode(HttpStatus.OK.value())
					.setMessage("Product found successfully")
					.setData(mapToProductResponse(Product)));
		}).orElseThrow(()-> new ProductNotFoundByIdException("Product is not present by this id"));
	

	}

	@Override
	public List<Product> findProductsByFilters(SearchFilter filter, Integer page,OrderBy orderBy,String sortBy) {
		
		Specification<Product> specification = productSpecification.buildSpecification(filter);
		Pageable pageble = (Pageable) PageRequest.of(page, 40,Sort.by(OrderBy.ASC==orderBy ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
		Page<Product> page1= productRepository.findAll(specification,pageble);
		List<Product> product =page1.getContent();
		
		
		return product;
	}
 
	
	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> SearchProduct(String searchString) {
		
		 List<ProductResponse> products = productRepository.findByProductNameIgnoreCaseContainingOrProductDescriptionIgnoreCaseContaining(searchString,searchString)
		 .stream().map(this::mapToProductResponse)
		 .toList();
		
		return ResponseEntity.status(HttpStatus.FOUND)
				.body(new ResponseStructure()
						.setStatuscode(HttpStatus.FOUND.value())
						.setMessage("Products found succsefully")
						.setData(products));
	}
	
	
	
	

}
