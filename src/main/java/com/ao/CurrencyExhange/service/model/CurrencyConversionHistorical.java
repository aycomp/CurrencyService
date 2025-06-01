package com.ao.CurrencyExhange.service.model;

public class CurrencyConversionHistorical {

	private CurrencyConversion currencyConversion;

	private boolean historical;

	public CurrencyConversion getCurrencyConversion() {
		return currencyConversion;
	}

	public void setCurrencyConversion(CurrencyConversion currencyConversion) {
		this.currencyConversion = currencyConversion;
	}

	public boolean isHistorical() {
		return historical;
	}

	public void setHistorical(boolean historical) {
		this.historical = historical;
	}
}
