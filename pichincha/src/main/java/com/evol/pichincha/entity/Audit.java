package com.evol.pichincha.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Audit {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String userName;
	private Long idCurrency;
	private String uri;
	private String method;
	private String changeValue;
	private String changeAt;
	
	public Audit(String userName, Long idCurrency, String uri, String method, String changeValue, String changeAt) {
		super();
		this.userName = userName;
		this.idCurrency = idCurrency;
		this.uri = uri;
		this.method = method;
		this.changeValue = changeValue;
		this.changeAt = changeAt;
	}
	
	
}
