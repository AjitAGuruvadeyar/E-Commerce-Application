package com.jsp.e_com.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.jsp.e_com.entity.Product;
import com.jsp.e_com.request.dto.SearchFilter;

import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Component
@NoArgsConstructor
public class ProductSpecification {

	
	

	public Specification<Product> buildSpecification(SearchFilter searchFilter) {
		
		
		return (root,query,criteriaBuilder)->{
			
			List<Predicate> predicate=new ArrayList<>();
			if(searchFilter.getMinPrice()>0) {
				predicate.add(criteriaBuilder.greaterThanOrEqualTo(root.get("productPrice"), searchFilter.getMinPrice()));
			}
			if(searchFilter.getMaxPrice()>0) {
				predicate.add(criteriaBuilder.lessThanOrEqualTo(root.get("productPrice"),searchFilter.getMaxPrice()));
			}
			if(searchFilter.getCategory()!=(null)) {
				predicate.add(criteriaBuilder.equal(root.get("category"), searchFilter.getCategory()));
			}
			if(searchFilter.getAvailability()!=(null)) {
				predicate.add(criteriaBuilder.equal(root.get("availabilityStatus"), searchFilter.getAvailability()));
			}
			if(searchFilter.getDiscount()>0) {
				
			}
			
			Predicate[] predicates = predicate.toArray(new Predicate[0]);
			return criteriaBuilder.and(predicates);
			
		};
		
		
	}
	

}
