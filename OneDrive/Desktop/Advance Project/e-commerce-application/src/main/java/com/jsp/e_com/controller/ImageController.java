package com.jsp.e_com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.e_com.repository.ImageRepository;
import com.jsp.e_com.service.ImageService;
import com.jsp.e_com.util.SimpleResponseStructer;

import lombok.AllArgsConstructor;
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(allowCredentials = "true" , origins ="http://localhost:5173")
public class ImageController {
	
	
	private ImageService imageService;
	
	@PostMapping("/products/{productId}/image-type/{imageType}/images")
	public ResponseEntity<SimpleResponseStructer> addImage(@RequestParam int productId,@RequestParam String imageType,@RequestParam MultipartFile image) {
		return imageService.addImage(productId,imageType,image);
	}
	
	@GetMapping("/image/{imageId}")
	public ResponseEntity<byte[]> findImage(@PathVariable String imageId){
		return imageService.findImage(imageId);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<byte[]> findById(@PathVariable int productId){
		return imageService.findByProductId(productId);
	}

}
