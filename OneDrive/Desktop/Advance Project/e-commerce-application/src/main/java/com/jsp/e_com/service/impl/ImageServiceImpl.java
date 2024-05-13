package com.jsp.e_com.service.impl;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.e_com.entity.Image;
import com.jsp.e_com.enums.ImageType;
import com.jsp.e_com.exception.ImageNotFoundByIdException;
import com.jsp.e_com.exception.ProductNotFoundByIdException;
import com.jsp.e_com.repository.ImageRepository;
import com.jsp.e_com.repository.ProductRepository;
import com.jsp.e_com.service.ImageService;
import com.jsp.e_com.util.SimpleResponseStructer;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService{

	private ImageRepository imageRepository;
	private ProductRepository productRepository;
	private SimpleResponseStructer simpleResponseStructer;

	@Override
	public ResponseEntity<SimpleResponseStructer> addImage(int productId, String imageType, MultipartFile image) {
		if(!productRepository.existsById(productId))throw new ProductNotFoundByIdException("Product not find by id");
		
		imageRepository.findByProductIdAndImageType(productId,ImageType.COVER).ifPresent(img->
		{
				img.setImageType(ImageType.NORMAL);
				imageRepository.save(img);
			});
	
		
		
		try {
			Image image2=new Image();
			image2.setProductId(productId);
			image2.setImageType(ImageType.valueOf(imageType.toUpperCase()));
			image2.setImageByte(image.getBytes());
			image2.setContentType(image.getContentType());
			imageRepository.save(image2);
		} catch (IOException e) {
			// TODO Auto-generated catch block findProductIdAndImageType
			e.printStackTrace();
		}

		
		return ResponseEntity.ok(simpleResponseStructer.setStatus(HttpStatus.OK.value())
				.setMassage("Image Added sucessfuly"));
	}

	@Override
	public ResponseEntity<byte[]> findImage(String imageId) {

		return imageRepository.findById(imageId).map(image -> {
			return ResponseEntity.ok()
					.contentLength(image.getImageByte().length)
					.contentType(MediaType.valueOf(image.getContentType()))
					.body(image.getImageByte());
		}).orElseThrow(()->new ImageNotFoundByIdException("image not prasent"));
	}

	@Override
	public ResponseEntity<byte[]> findByProductId(int productId) {
		// TODO Auto-generated method stub
		return imageRepository.findByProductIdAndImageType(productId,ImageType.COVER).map(image -> {
			return ResponseEntity.ok()
					.contentLength(image.getImageByte().length)
					.contentType(MediaType.valueOf(image.getContentType()))
					.body(image.getImageByte());
		}).orElseThrow(()->new ImageNotFoundByIdException("image not prasent"));

	}
	
	

}
