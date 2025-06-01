package com.ao.CurrencyExhange.service.model;

import java.math.BigDecimal;

public class CurrencyConversion {

	private String sourceCurrency;

	private String targetCurrency;

	private BigDecimal sourceAmount;

	private double rate;

	private BigDecimal targetAmount;

	private long transactionId;

	private BigDecimal quote;

	private String date;

	public CurrencyConversion() {}

	public CurrencyConversion(String sourceCurrency, String targetCurrency, BigDecimal sourceAmount, BigDecimal targetAmount, long transactionId) {
		this.sourceCurrency = sourceCurrency;
		this.targetCurrency = targetCurrency;
		this.sourceAmount = sourceAmount;
		this.targetAmount = targetAmount;
		this.transactionId = transactionId;
	}

	public CurrencyConversion(String sourceCurrency, String targetCurrency, BigDecimal sourceAmount, BigDecimal targetAmount, String date) {
		this.sourceCurrency = sourceCurrency;
		this.targetCurrency = targetCurrency;
		this.sourceAmount = sourceAmount;
		this.targetAmount = targetAmount;
		this.date = date;
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

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
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

	public BigDecimal getQuote() {
		return quote;
	}

	public void setQuote(BigDecimal quote) {
		this.quote = quote;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
