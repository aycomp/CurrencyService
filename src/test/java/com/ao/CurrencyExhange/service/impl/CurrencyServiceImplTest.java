package com.ao.CurrencyExhange.service.impl;

import com.ao.CurrencyExhange.client.CurrencyLayerApiClientImpl;
import com.ao.CurrencyExhange.client.exception.CurrencyLayerException;
import com.ao.CurrencyExhange.client.model.CurrencyLayerConversionResponse;
import com.ao.CurrencyExhange.client.model.CurrencyLayerInfo;
import com.ao.CurrencyExhange.client.model.CurrencyLayerLiveResponse;
import com.ao.CurrencyExhange.service.model.CurrencyConversion;
import com.ao.CurrencyExhange.service.model.ExchangeRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceImplTest {

	@InjectMocks
	private CurrencyServiceImpl currencyService;

	@Mock
	private CurrencyLayerApiClientImpl currencyLayerApiClient;

	private String sourceCurrency;

	private String targetCurrency;

	private BigDecimal sourceAmount;

	private BigDecimal targetAmount;

	private String date;

	private String fileContent;

	@BeforeEach
	void setUp() {
		sourceCurrency = "USD";
		targetCurrency = "TL";
		sourceAmount = new BigDecimal("100");
		targetAmount = new BigDecimal("23");
		date = "2025-05-31";

	}

	@Test
	public void getExchangeRate_success() {
		CurrencyLayerLiveResponse currencyLayerLiveResponse = new CurrencyLayerLiveResponse();
		currencyLayerLiveResponse.setSuccess(Boolean.TRUE);
		Map<String, Double> quotes = new HashMap<>();
		quotes.put(sourceCurrency + targetCurrency, 0.3);
		currencyLayerLiveResponse.setQuotes(quotes);

		when(currencyLayerApiClient.getLiveRates(sourceCurrency, targetCurrency)).thenReturn(currencyLayerLiveResponse);
		ExchangeRate exchangeRate = currencyService.getExchangeRate(sourceCurrency, targetCurrency);
		assertEquals(sourceCurrency, exchangeRate.getSourceCurrency());
		assertEquals(targetCurrency, exchangeRate.getTargetCurrency());
		assertEquals(0.3, exchangeRate.getRate());
	}

	@Test
	public void getExchangeRate_failure_invalidSourceCurrency() {
		sourceCurrency = "EUR";
		assertThrows(IllegalArgumentException.class, () -> currencyService.getExchangeRate(sourceCurrency, targetCurrency));
	}

	@Test
	public void getExchangeRate_failure_invalidCurrencyRateReturned() {
		CurrencyLayerLiveResponse currencyLayerLiveResponse = new CurrencyLayerLiveResponse();
		currencyLayerLiveResponse.setSuccess(Boolean.TRUE);
		Map<String, Double> quotes = new HashMap<>();
		currencyLayerLiveResponse.setQuotes(quotes);

		when(currencyLayerApiClient.getLiveRates(sourceCurrency, targetCurrency)).thenReturn(currencyLayerLiveResponse);

		assertThrows(CurrencyLayerException.class, () -> currencyService.getExchangeRate(sourceCurrency, targetCurrency));
	}

	@Test
	public void getExchangeRate_failure_failedApiResponseReturned() {
		CurrencyLayerLiveResponse currencyLayerLiveResponse = new CurrencyLayerLiveResponse();
		currencyLayerLiveResponse.setSuccess(Boolean.FALSE);

		when(currencyLayerApiClient.getLiveRates(sourceCurrency, targetCurrency)).thenReturn(currencyLayerLiveResponse);
		assertThrows(CurrencyLayerException.class, () -> currencyService.getExchangeRate(sourceCurrency, targetCurrency));
	}

	@Test
	public void getCurrencyConversion_success() {
		CurrencyLayerConversionResponse apiResponse = new CurrencyLayerConversionResponse();
		apiResponse.setSuccess(Boolean.TRUE);
		apiResponse.setResult(new BigDecimal(23));
		CurrencyLayerInfo currencyLayerInfo = new CurrencyLayerInfo();
		currencyLayerInfo.setTimestamp(System.currentTimeMillis());
		apiResponse.setInfo(currencyLayerInfo);

		when(currencyLayerApiClient.getCurrencyConversion(sourceCurrency, targetCurrency, sourceAmount)).thenReturn(apiResponse);
		CurrencyConversion currencyConversion = currencyService.getCurrencyConversion(sourceCurrency, targetCurrency, sourceAmount);
		assertEquals(sourceCurrency, currencyConversion.getSourceCurrency());
		assertEquals(targetCurrency, currencyConversion.getTargetCurrency());
		assertEquals(targetAmount, currencyConversion.getTargetAmount());
	}

	@Test
	public void getCurrencyConversion_failure_nullApiResponseReturned() {
		CurrencyLayerConversionResponse apiResponse = null;
		when(currencyLayerApiClient.getCurrencyConversion(sourceCurrency, targetCurrency, sourceAmount)).thenReturn(apiResponse);
		assertThrows(CurrencyLayerException.class, () -> currencyService.getCurrencyConversion(sourceCurrency, targetCurrency, sourceAmount));
	}

	@Test
	public void getCurrencyConversion_failure_failedApiResponseReturned() {
		CurrencyLayerConversionResponse apiResponse = new CurrencyLayerConversionResponse();
		apiResponse.setSuccess(Boolean.FALSE);
		when(currencyLayerApiClient.getCurrencyConversion(sourceCurrency, targetCurrency, sourceAmount)).thenReturn(apiResponse);
		assertThrows(CurrencyLayerException.class, () -> currencyService.getCurrencyConversion(sourceCurrency, targetCurrency, sourceAmount));
	}

	@Test
	public void getHistoricalCurrencyConversion_success() {
		CurrencyLayerConversionResponse apiResponse = new CurrencyLayerConversionResponse();
		apiResponse.setSuccess(Boolean.TRUE);
		apiResponse.setResult(new BigDecimal(23));
		CurrencyLayerInfo currencyLayerInfo = new CurrencyLayerInfo();
		currencyLayerInfo.setTimestamp(System.currentTimeMillis());
		apiResponse.setInfo(currencyLayerInfo);

		when(currencyLayerApiClient.getHistoricalCurrencyConversion(sourceCurrency, targetCurrency, sourceAmount, date)).thenReturn(apiResponse);
		CurrencyConversion currencyConversion = currencyService.getHistoricalCurrencyConversion(sourceCurrency, targetCurrency, sourceAmount, date);
		assertEquals(sourceCurrency, currencyConversion.getSourceCurrency());
		assertEquals(targetCurrency, currencyConversion.getTargetCurrency());
		assertEquals(targetAmount, currencyConversion.getTargetAmount());
	}

	@Test
	public void getHistoricalCurrencyConversion_failure_failedApiResponseReturned() {
		CurrencyLayerConversionResponse apiResponse = new CurrencyLayerConversionResponse();
		apiResponse.setSuccess(Boolean.FALSE);
		when(currencyLayerApiClient.getHistoricalCurrencyConversion(sourceCurrency, targetCurrency, sourceAmount, date)).thenReturn(apiResponse);
		assertThrows(CurrencyLayerException.class, () -> currencyService.getHistoricalCurrencyConversion(sourceCurrency, targetCurrency, sourceAmount, date));
	}

	@Test
	public void processFileAndGetCurrencyConversion_success() throws IOException {
		fileContent = "amount;sourceCurrency;targetCurrency\n100;USD;TL";
		MockMultipartFile multipartFile = new MockMultipartFile("file", "currency-conversion-file.txt", "text/plain", fileContent.getBytes());

		List<CurrencyConversion> expectedCurrencyConversionList = new ArrayList<>();
		CurrencyConversion currencyConversion = new CurrencyConversion();
		currencyConversion.setSourceCurrency(sourceCurrency);
		currencyConversion.setTargetCurrency(targetCurrency);
		currencyConversion.setTargetAmount(new BigDecimal(23));
		expectedCurrencyConversionList.add(currencyConversion);

		CurrencyLayerConversionResponse apiResponse = new CurrencyLayerConversionResponse();
		apiResponse.setSuccess(Boolean.TRUE);
		apiResponse.setResult(new BigDecimal(23));
		CurrencyLayerInfo currencyLayerInfo = new CurrencyLayerInfo();
		currencyLayerInfo.setTimestamp(System.currentTimeMillis());
		apiResponse.setInfo(currencyLayerInfo);

		when(currencyLayerApiClient.getCurrencyConversion(sourceCurrency, targetCurrency, sourceAmount)).thenReturn(apiResponse);


		List<CurrencyConversion> actualCurrencyConversionList = currencyService.processFileAndGetCurrencyConversion(multipartFile);
		assertEquals(actualCurrencyConversionList.get(0).getTargetAmount(), expectedCurrencyConversionList.get(0).getTargetAmount());
	}

	@Test
	public void processFileAndGetCurrencyConversion_failure_corruptedFile() throws IOException {
		fileContent = "amount;sourceCurrency;targetCurrency\nUSD;100;EUR\n100;USD;TL";
		MockMultipartFile multipartFile = new MockMultipartFile("file", "currency-conversion-file.txt", "text/plain", fileContent.getBytes());

		List<CurrencyConversion> expectedCurrencyConversionList = new ArrayList<>();
		CurrencyConversion currencyConversion = new CurrencyConversion();
		currencyConversion.setSourceCurrency(sourceCurrency);
		currencyConversion.setTargetCurrency(targetCurrency);
		currencyConversion.setTargetAmount(new BigDecimal(23));
		expectedCurrencyConversionList.add(currencyConversion);

		CurrencyLayerConversionResponse apiResponse = new CurrencyLayerConversionResponse();
		apiResponse.setSuccess(Boolean.TRUE);
		apiResponse.setResult(new BigDecimal(23));
		CurrencyLayerInfo currencyLayerInfo = new CurrencyLayerInfo();
		currencyLayerInfo.setTimestamp(System.currentTimeMillis());
		apiResponse.setInfo(currencyLayerInfo);

		when(currencyLayerApiClient.getCurrencyConversion(sourceCurrency, targetCurrency, sourceAmount)).thenReturn(apiResponse);

		List<CurrencyConversion> actualCurrencyConversionList = currencyService.processFileAndGetCurrencyConversion(multipartFile);
		assertEquals(1, actualCurrencyConversionList.size());
	}
}
