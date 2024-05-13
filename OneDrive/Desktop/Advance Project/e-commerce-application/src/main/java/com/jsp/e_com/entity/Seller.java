package com.jsp.e_com.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Setter
@NoArgsConstructor
@Getter
public class Seller extends User{
@OneToOne
private Address address;
}
