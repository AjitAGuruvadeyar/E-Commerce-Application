package com.jsp.e_com.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.e_com.util.SimpleResponseStructer;

public interface ImageService {

	ResponseEntity<SimpleResponseStructer> addImage(int productId, String imageType, MultipartFile image);

	ResponseEntity<byte[]> findImage(String imageId);

	ResponseEntity<byte[]> findByProductId(int productId);

}
