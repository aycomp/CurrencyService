package com.ao.CurrencyExhange.service.spec;

import com.ao.CurrencyExhange.service.model.CurrencyConversion;
import com.ao.CurrencyExhange.service.model.ExchangeRate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface CurrencyService {

	ExchangeRate getExchangeRate(@NotBlank String sourceCurrency, @NotBlank String targetCurrency);

	CurrencyConversion getCurrencyConversion(@NotBlank String sourceCurrency, @NotBlank String targetCurrency, @NotNull @Positive BigDecimal amount);

	CurrencyConversion getHistoricalCurrencyConversion(@NotBlank String sourceCurrency, @NotBlank String targetCurrency, @NotNull @Positive BigDecimal amount, @NotBlank String date);

	List<CurrencyConversion> processFileAndGetCurrencyConversion(@NotNull MultipartFile file) throws IOException;

}
