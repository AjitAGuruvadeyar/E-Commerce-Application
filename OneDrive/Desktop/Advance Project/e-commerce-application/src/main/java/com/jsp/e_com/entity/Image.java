package com.jsp.e_com.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.jsp.e_com.enums.ImageType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(collection = "Images")
public class Image {
	@MongoId
	private String imageId;
	private ImageType imageType;
	private byte[] imageByte;
	private int productId;

	private String contentType;
	
}
