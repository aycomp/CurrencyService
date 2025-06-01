package com.ao.CurrencyExhange.service.impl;

import com.ao.CurrencyExhange.client.CurrencyLayerApiClientImpl;
import com.ao.CurrencyExhange.client.exception.CurrencyLayerException;
import com.ao.CurrencyExhange.client.model.CurrencyLayerConversionResponse;
import com.ao.CurrencyExhange.client.model.CurrencyLayerLiveResponse;
import com.ao.CurrencyExhange.service.model.CurrencyConversion;
import com.ao.CurrencyExhange.service.model.ExchangeRate;
import com.ao.CurrencyExhange.service.spec.CurrencyService;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Validated
public class CurrencyServiceImpl implements CurrencyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceImpl.class);

	private static final String ALLOWED_CURRENCY = "USD";

	private static final String CSV_HEADER_START = "amount";

	private static final String CSV_COLUMN_SEPARATOR = ";";

	private final CurrencyLayerApiClientImpl currencyLayerApiClientImpl;

	@Autowired
	public CurrencyServiceImpl(CurrencyLayerApiClientImpl currencyLayerApiClientImpl) {
		this.currencyLayerApiClientImpl = currencyLayerApiClientImpl;
	}

	@Override
	public ExchangeRate getExchangeRate(@NotBlank String sourceCurrency, @NotBlank String targetCurrency) {
		validateSourceCurrency(sourceCurrency);

		CurrencyLayerLiveResponse apiResponse = currencyLayerApiClientImpl.getLiveRates(sourceCurrency, targetCurrency);
		validateAndLogApiResponse(sourceCurrency, targetCurrency, apiResponse);

		String queriedCurrency = sourceCurrency.toUpperCase() + targetCurrency.toUpperCase();
		Double currencyRate = apiResponse.getQuotes().get(queriedCurrency);
		validateCurrencyRateAndLog(currencyRate, queriedCurrency);

		return new ExchangeRate(sourceCurrency.toUpperCase(), targetCurrency.toUpperCase(), currencyRate);
	}

	@Override
	public CurrencyConversion getCurrencyConversion(@NotBlank String sourceCurrency, @NotBlank String targetCurrency, @NotNull @Positive BigDecimal amount) {
		validateSourceCurrency(sourceCurrency);

		CurrencyLayerConversionResponse apiResponse = currencyLayerApiClientImpl.getCurrencyConversion(sourceCurrency, targetCurrency, amount);
		validateAndLogApiResponse(sourceCurrency, targetCurrency, apiResponse);

		// I mapped info -> timestamp returned from api to my dto object as transactionId;
		// since api is not returning any transactionId as mentioned in the project description
		return new CurrencyConversion(sourceCurrency.toUpperCase(), targetCurrency.toUpperCase(), amount, apiResponse.getResult(), apiResponse.getInfo().getTimestamp());
	}

	@Override
	public CurrencyConversion getHistoricalCurrencyConversion(@NotBlank String sourceCurrency, @NotBlank String targetCurrency, @NotNull @Positive BigDecimal sourceAmount, @NotBlank String date) {
		validateSourceCurrency(sourceCurrency);

		CurrencyLayerConversionResponse apiResponse = currencyLayerApiClientImpl.getHistoricalCurrencyConversion(sourceCurrency, targetCurrency, sourceAmount, date);
		validateAndLogApiResponse(sourceCurrency, targetCurrency, apiResponse);

		return new CurrencyConversion(sourceCurrency.toUpperCase(), targetCurrency.toUpperCase(), sourceAmount, apiResponse.getResult(), apiResponse.getDate());
	}

	@Override
	public List<CurrencyConversion> processFileAndGetCurrencyConversion(@NotNull MultipartFile file) throws IOException {
		List<CurrencyConversion> currencyConversionList = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			String row;
			while ((row = reader.readLine()) != null) {
				if (StringUtils.isBlank(row) || row.toLowerCase().startsWith(CSV_HEADER_START)) {
					continue;
				}

				try {
					String[] columns = row.split(CSV_COLUMN_SEPARATOR);
					BigDecimal amount = new BigDecimal(columns[0]);
					String sourceCurrency = columns[1];
					String targetCurrency = columns[2];

					CurrencyConversion currencyConversion = getCurrencyConversion(sourceCurrency, targetCurrency, amount);
					currencyConversionList.add(currencyConversion);

					// Added wait time for demo purposes,
					// since api is not responding properly in trial mode while sending requests repeatedly.
					Thread.sleep(1000);
				} catch (Exception e) {
					LOGGER.error("Could not parse CSV row: {} Error: {}", row, e.getMessage());
				}
			}
		}

		return currencyConversionList;
	}

	private static void validateSourceCurrency(String sourceCurrency) {
		if (!sourceCurrency.equalsIgnoreCase(ALLOWED_CURRENCY)) {
			LOGGER.warn("Invalid source currency provided. Source currency: {}", sourceCurrency);
			throw new IllegalArgumentException("Invalid currency code. Only USD is supported for base currency in trial mode.");
		}
	}

	private static void validateCurrencyRateAndLog(Double currencyRate, String queriedCurrency) {
		if (currencyRate == null) {
			LOGGER.error("Could not get exchange rate from Currency API. Queried Currency: {}", queriedCurrency);
			throw new CurrencyLayerException("Could not get exchange rate from currency " + queriedCurrency);
		}
	}

	private static void validateAndLogApiResponse(String sourceCurrency, String targetCurrency, Object apiResponse) {
		if (apiResponse == null) {
			LOGGER.error("Currency Layer Api returned error. Api Response is null");
			throw new CurrencyLayerException("Currency Layer Api returned error. Api Response is null.");
		}

		if (apiResponse instanceof CurrencyLayerLiveResponse && !((CurrencyLayerLiveResponse) apiResponse).isSuccess()) {
			LOGGER.error("Currency Layer Api returned error. Api Response: {} Source Currency: {} Target Currency: {} ", apiResponse, sourceCurrency, targetCurrency);
			throw new CurrencyLayerException("Could not get exchange rate from currency " + sourceCurrency + " to " + targetCurrency);
		}

		if (apiResponse instanceof CurrencyLayerConversionResponse && !((CurrencyLayerConversionResponse) apiResponse).isSuccess()) {
			LOGGER.error("Currency Conversion API returned error. Api Response: {} Source Currency: {} Target Currency: {}", apiResponse, sourceCurrency, targetCurrency);
			throw new CurrencyLayerException("Could not get exchange rate from currency " + sourceCurrency + " to " + targetCurrency);
		}
	}


}
