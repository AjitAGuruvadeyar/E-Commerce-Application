package com.jsp.e_com.entity;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Customer extends User{
	@OneToMany
	private List<Address> address=new ArrayList(); 
	@OneToMany
	private List<CartProduct> cartProducts=new ArrayList();
	@OneToMany
	private List<Order> order=new ArrayList(); 

}
