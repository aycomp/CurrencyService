package com.ao.CurrencyExhange.client;

import com.ao.CurrencyExhange.client.model.CurrencyLayerConversionResponse;
import com.ao.CurrencyExhange.client.model.CurrencyLayerLiveResponse;

import java.math.BigDecimal;

public interface CurrencyLayerApiClient {

	CurrencyLayerLiveResponse getLiveRates(String sourceCurrency, String targetCurrency);

	CurrencyLayerConversionResponse getCurrencyConversion(String sourceCurrency, String targetCurrency, BigDecimal amount);

	CurrencyLayerConversionResponse getHistoricalCurrencyConversion(String sourceCurrency, String targetCurrency, BigDecimal amount, String date);

}
