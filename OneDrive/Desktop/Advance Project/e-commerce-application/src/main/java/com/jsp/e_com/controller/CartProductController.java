package com.jsp.e_com.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.e_com.entity.CartProduct;
import com.jsp.e_com.entity.Product;
import com.jsp.e_com.service.CartProductService;
import com.jsp.e_com.util.ResponseStructure;

@RestController
public class CartProductController {
	
	private CartProductService cartProductService;
	
	@PostMapping("/product/cartProductc")
	ResponseEntity<ResponseStructure<CartProduct>> addProductToCart(@RequestParam int productId){
		return cartProductService.addProductToCart(productId);
	}

	@GetMapping("/product/cartProductc")
	ResponseEntity<ResponseStructure<List<CartProduct>>> fetchCartProducts(){
		return cartProductService.fetchCartProducts();
	}
	
    @PutMapping("/product/cartProductc")
	ResponseEntity<ResponseStructure<CartProduct>> updateSelectedQuantity(int cartProductId, int selectedQuantity){
		return cartProductService.updateSelectedQuantity(cartProductId, selectedQuantity);
	}


}
