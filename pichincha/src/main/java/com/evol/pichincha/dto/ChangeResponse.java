package com.evol.pichincha.dto;

import com.evol.pichincha.util.Constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChangeResponse {
	private String value;
	private String valueOriginal;
	private String currency;
	private String currencyId;
	private String currencyOriginal;
	private String currencyOriginalId;
	private String message = Constants.SUCCESS;
}
