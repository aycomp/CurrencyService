package com.ao.CurrencyExhange.client.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyLayerConversionResponse extends CurrencyLayerGenericResponse {

	@JsonProperty("query")
	private CurrencyLayerQuery query;

	@JsonProperty("info")
	private CurrencyLayerInfo info;

	@JsonProperty("historical")
	private boolean historical = false;

	@JsonProperty("date")
	private String date;

	@JsonProperty("result")
	private BigDecimal result;

	public CurrencyLayerQuery getQuery() {
		return query;
	}

	public void setQuery(CurrencyLayerQuery query) {
		this.query = query;
	}

	public CurrencyLayerInfo getInfo() {
		return info;
	}

	public void setInfo(CurrencyLayerInfo info) {
		this.info = info;
	}

	public boolean getHistorical() {
		return historical;
	}

	public void setHistorical(boolean historical) {
		this.historical = historical;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BigDecimal getResult() {
		return result;
	}

	public void setResult(BigDecimal result) {
		this.result = result;
	}

}