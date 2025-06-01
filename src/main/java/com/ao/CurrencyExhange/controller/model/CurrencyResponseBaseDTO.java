package com.ao.CurrencyExhange.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyResponseBaseDTO {

	@JsonProperty("SourceCurrency")
	private String sourceCurrency;

	@JsonProperty("TargetCurrency")
	private String targetCurrency;

	public CurrencyResponseBaseDTO(String sourceCurrency, String targetCurrency) {
		this.sourceCurrency = sourceCurrency;
		this.targetCurrency = targetCurrency;
	}

	public String getSourceCurrency() {
		return sourceCurrency;
	}

	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}

	public String getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

}
