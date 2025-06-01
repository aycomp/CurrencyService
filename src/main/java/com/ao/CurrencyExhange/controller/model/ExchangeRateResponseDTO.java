package com.ao.CurrencyExhange.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExchangeRateResponseDTO extends CurrencyResponseBaseDTO {

	@JsonProperty("ExchangeRate")
	private Double rate;

	public ExchangeRateResponseDTO(String sourceCurrency, String targetCurrency, Double rate) {
		super(sourceCurrency, targetCurrency);
		this.rate = rate;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
}
