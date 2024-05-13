package com.jsp.e_com.repository;

import java.lang.foreign.Linker.Option;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jsp.e_com.entity.Image;
import com.jsp.e_com.enums.ImageType;

public interface ImageRepository extends MongoRepository<Image, String>{

	Optional<Image> findByProductIdAndImageType(int productId, ImageType cover);

	Optional<Image> findByProductId(int productId);

}
