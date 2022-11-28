package com.evol.pichincha.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
@Entity
public class TypeCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String code;
    private double valueCurrency;
    private String createdAt;
    private String updateAt;
    
	@Override
	public String toString() {
		return "TypeCurrency [id=" + id + ", name=" + name + ", code=" + code + ", valueCurrency=" + valueCurrency
				+ "]";
	}
    
    
}
