package com.ao.CurrencyExhange.service.model;

import java.math.BigDecimal;

public class CurrencyConversionInfo {

	private long timestamp;

	private BigDecimal quote;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public BigDecimal getQuote() {
		return quote;
	}

	public void setQuote(BigDecimal quote) {
		this.quote = quote;
	}

}
