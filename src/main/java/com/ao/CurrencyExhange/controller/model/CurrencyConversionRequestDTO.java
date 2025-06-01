package com.ao.CurrencyExhange.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyConversionRequestDTO extends CurrencyRequestBaseDTO {

	@JsonProperty("Amount")
	private BigDecimal amount;

	@JsonProperty("Date")
	private String date;

	public BigDecimal getAmount() { return amount; }

	public void setAmount(BigDecimal amount) { this.amount = amount; }

	public String getDate() { return date; }

	public void setDate(String date) { this.date = date; }
}
