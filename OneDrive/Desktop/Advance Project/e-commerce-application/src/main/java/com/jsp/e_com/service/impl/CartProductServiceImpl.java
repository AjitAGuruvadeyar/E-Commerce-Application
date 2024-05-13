package com.jsp.e_com.service.impl;

import java.util.List;

import org.springframework.aop.aspectj.SingletonAspectInstanceFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jsp.e_com.entity.CartProduct;
import com.jsp.e_com.repository.CartProductRepository;
import com.jsp.e_com.repository.CustomerRepository;
import com.jsp.e_com.repository.ProductRepository;
import com.jsp.e_com.service.CartProductService;
import com.jsp.e_com.util.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartProductServiceImpl implements CartProductService {

	private ResponseStructure<CartProduct> respStructure;
	private ResponseStructure<List<CartProduct>> responsesStructure;
	private CartProductRepository cartProductRepo;
	private ProductRepository productRepository;
	private CustomerRepository customerRepository;
	@Override
	public ResponseEntity<ResponseStructure<CartProduct>> addProductToCart(int productId) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return customerRepository.findByUsername(username).map(customer->{
			return 	productRepository.findById(productId).map(product->{

				if(product.getProductQuantity()<1&& customer.getCartProducts().size()>10) throw new RuntimeException();
				CartProduct cart = new CartProduct();
				cart.setProduct(product);
				cart.setSelectedQuantity(1);
				cart=cartProductRepo.save(cart);
				customer.getCartProducts().add(cart);
				customerRepository.save(customer);
				return ResponseEntity.ok(respStructure.setStatuscode(HttpStatus.OK.value())
						.setMessage("cart product is added")
						.setData(cart));
			
			}).orElseThrow(()-> new RuntimeException());
		}).orElseThrow(()-> new RuntimeException());		
	}
	
	
	@Override
	public ResponseEntity<ResponseStructure<List<CartProduct>>> fetchCartProducts() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		return customerRepository.findByUsername(username).map(customer -> {
//			return ResponseEntity.ok(respStructure.setStatuscode(HttpStatus.OK.value())
//					.setMessage("Found all cart products")
//					.setData(customer.getCartProducts()));
//		}).orElseThrow(()-> new RuntimeException());
		
		return null;
	}


	@Override
	public ResponseEntity<ResponseStructure<CartProduct>> updateSelectedQuantity(int cartProductId,
			int selectedQuantity) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		return customerRepository.findByUsername(username).map(customer->{
//			return 	cartProductRepo.findById(cartProductId).map(cart->{
//				int productQuantity = cart.getProduct().getProductQuantity();
//				if(selectedQuantity>productQuantity && selectedQuantity<=0) throw new RuntimeException();
//				cart.setSelectedQuantity(selectedQuantity);
//				cart=cartProductRepo.save(cart);
//				return ResponseEntity.ok(respStructure.setStatusCode(HttpStatus.OK.value())
//						.setMessage("Product Added to cart")
//						.setData(cart));
//			}).orElseThrow(()-> new RuntimeException());
//		}).orElseThrow(()-> new RuntimeException());
		
		return null;

	}

}