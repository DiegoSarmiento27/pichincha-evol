package com.evol.pichincha.dto;

import lombok.Data;

@Data
public class ChangeRequest {
	private double value;
	private long currencyId;
	private long currencyChangeId;
}
