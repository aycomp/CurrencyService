package com.ao.CurrencyExhange.client.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyLayerLiveResponse extends CurrencyLayerGenericResponse {

	@JsonProperty("quotes")
	private Map<String, Double> quotes;

	public Map<String, Double> getQuotes() {
		return quotes;
	}

	public void setQuotes(Map<String, Double> quotes) {
		this.quotes = quotes;
	}

}