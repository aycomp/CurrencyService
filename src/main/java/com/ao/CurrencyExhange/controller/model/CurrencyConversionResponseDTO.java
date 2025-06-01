package com.ao.CurrencyExhange.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CurrencyConversionResponseDTO extends CurrencyResponseBaseDTO{

	@JsonProperty("SourceAmount")
	private BigDecimal sourceAmount;

	@JsonProperty("TargetAmount")
	private BigDecimal targetAmount;

	@JsonProperty("TransactionId")
	private long transactionId;

	public CurrencyConversionResponseDTO(String sourceCurrency, String targetCurrency,
			BigDecimal sourceAmount, BigDecimal targetAmount, long transactionId) {
		super(sourceCurrency, targetCurrency);
		this.sourceAmount = sourceAmount;
		this.targetAmount = targetAmount;
		this.transactionId = transactionId;
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

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

}
