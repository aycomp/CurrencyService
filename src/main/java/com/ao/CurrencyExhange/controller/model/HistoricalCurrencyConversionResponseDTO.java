package com.ao.CurrencyExhange.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class HistoricalCurrencyConversionResponseDTO extends CurrencyResponseBaseDTO {

	@JsonProperty("SourceAmount")
	private BigDecimal sourceAmount;

	@JsonProperty("TargetAmount")
	private BigDecimal targetAmount;

	@JsonProperty("Date")
	private String date;

	public HistoricalCurrencyConversionResponseDTO(String sourceCurrency, String targetCurrency,
			BigDecimal sourceAmount, BigDecimal targetAmount, String date) {
		super(sourceCurrency, targetCurrency);
		this.sourceAmount = sourceAmount;
		this.targetAmount = targetAmount;
		this.date = date;
	}

	public BigDecimal getSourceAmount() {
		return sourceAmount;
	}

	public void setSourceAmount(BigDecimal sourceAmount) {
		this.sourceAmount = sourceAmount;
	}

	public BigDecimal getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(BigDecimal targetAmount) {
		this.targetAmount = targetAmount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
