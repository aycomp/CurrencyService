package com.ao.CurrencyExhange.client;

import com.ao.CurrencyExhange.client.exception.CurrencyLayerException;
import com.ao.CurrencyExhange.client.model.CurrencyLayerConversionResponse;
import com.ao.CurrencyExhange.client.model.CurrencyLayerLiveResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class CurrencyLayerApiClientImpl implements CurrencyLayerApiClient {

	private static final String BASE_URL = "https://api.currencylayer.com/";

	private static final String LIVE_ENDPOINT = "live";

	private static final String CONVERT_ENDPOINT = "convert";

	private final OkHttpClient okHttpClient;

	private final ObjectMapper objectMapper;

	private final String accessKey;

	public CurrencyLayerApiClientImpl(@Value("${currencylayer.api.access_key}") String accessKey) {
		this.okHttpClient = new OkHttpClient();
		this.objectMapper = new ObjectMapper();
		this.accessKey = accessKey;
	}

	@Override
	public CurrencyLayerLiveResponse getLiveRates(String sourceCurrency, String targetCurrency) {
		String url = BASE_URL + LIVE_ENDPOINT + accessKey + "&source=" + sourceCurrency + "&currencies=" + targetCurrency;
		return sendRequest(url, CurrencyLayerLiveResponse.class);
	}

	@Override
	public CurrencyLayerConversionResponse getCurrencyConversion(String sourceCurrency, String targetCurrency, BigDecimal amount) {
		String url = BASE_URL + CONVERT_ENDPOINT + accessKey + "&from=" + sourceCurrency + "&to=" + targetCurrency + "&amount=" + amount;
		return sendRequest(url, CurrencyLayerConversionResponse.class);
	}

	@Override
	public CurrencyLayerConversionResponse getHistoricalCurrencyConversion(String sourceCurrency, String targetCurrency, BigDecimal amount, String date) {
		String url = BASE_URL + CONVERT_ENDPOINT + accessKey + "&from=" + sourceCurrency + "&to=" + targetCurrency + "&amount=" + amount + "&date=" + date;
		return sendRequest(url, CurrencyLayerConversionResponse.class);
	}

	private <T> T sendRequest(String url, Class<T> responseType) {
		Request request = new Request.Builder().url(url).build();

		try (Response response = okHttpClient.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				throw new CurrencyLayerException("Currency layer Response is failed: Response:  " + response);
			}
			return objectMapper.readValue(response.body().string(), responseType);
		} catch (IOException e) {
			throw new CurrencyLayerException("There was an error while sending data to Currency Layer", e);
		}
	}

}
