package com.jsp.e_com.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.jsp.e_com.entity.CartProduct;
import com.jsp.e_com.entity.Product;
import com.jsp.e_com.util.ResponseStructure;

public interface CartProductService {

	ResponseEntity<ResponseStructure<CartProduct>> addProductToCart(int productId);

	ResponseEntity<ResponseStructure<List<CartProduct>>> fetchCartProducts();

	ResponseEntity<ResponseStructure<CartProduct>> updateSelectedQuantity(int cartProductId, int selectedQuantity);



}
