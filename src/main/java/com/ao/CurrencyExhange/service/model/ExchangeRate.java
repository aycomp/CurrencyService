package com.ao.CurrencyExhange.service.model;

public class ExchangeRate {

	private String sourceCurrency;

	private String targetCurrency;

	private double rate;

	public ExchangeRate(String sourceCurrency, String targetCurrency, double rate) {
		this.sourceCurrency = sourceCurrency;
		this.targetCurrency = targetCurrency;
		this.rate = rate;
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

}
