package com.ao.CurrencyExhange.controller;

import com.ao.CurrencyExhange.controller.model.CurrencyConversionRequestDTO;
import com.ao.CurrencyExhange.controller.model.CurrencyConversionResponseDTO;
import com.ao.CurrencyExhange.controller.model.ExchangeRateRequestDTO;
import com.ao.CurrencyExhange.controller.model.ExchangeRateResponseDTO;
import com.ao.CurrencyExhange.controller.model.HistoricalCurrencyConversionResponseDTO;
import com.ao.CurrencyExhange.service.model.CurrencyConversion;
import com.ao.CurrencyExhange.service.spec.CurrencyService;
import com.ao.CurrencyExhange.service.model.ExchangeRate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
public class CurrencyController {

	private final CurrencyService currencyService;

	public CurrencyController(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	@PostMapping("/exchange-rate")
	public ResponseEntity<ExchangeRateResponseDTO> getExchangeRate(@Valid @RequestBody ExchangeRateRequestDTO exchangeRateRequestDTO) {
		String sourceCurrency = exchangeRateRequestDTO.getSourceCurrency();
		String targetCurrency = exchangeRateRequestDTO.getTargetCurrency();

		ExchangeRate exchangeRate = currencyService.getExchangeRate(sourceCurrency, targetCurrency);
		return ResponseEntity.status(HttpStatus.OK).body(
				new ExchangeRateResponseDTO(sourceCurrency, targetCurrency, exchangeRate.getRate())
		);
	}

	@PostMapping("/currency-conversion")
	public ResponseEntity<CurrencyConversionResponseDTO> getCurrencyConversion(@Valid @RequestBody CurrencyConversionRequestDTO currencyConversionRequestDTO) {
		String sourceCurrency = currencyConversionRequestDTO.getSourceCurrency();
		String targetCurrency = currencyConversionRequestDTO.getTargetCurrency();
		BigDecimal sourceAmount = currencyConversionRequestDTO.getAmount();

		CurrencyConversion currencyConversion = currencyService.getCurrencyConversion(sourceCurrency, targetCurrency, sourceAmount);
		BigDecimal targetAmount = currencyConversion.getTargetAmount();
		long transactionId = currencyConversion.getTransactionId();
		return ResponseEntity.status(HttpStatus.OK).body(
				new CurrencyConversionResponseDTO(sourceCurrency, targetCurrency, sourceAmount, targetAmount, transactionId)
		);
	}

	@PostMapping("/historical-currency-conversion")
	public ResponseEntity<HistoricalCurrencyConversionResponseDTO> getConversionHistory(@Valid @RequestBody CurrencyConversionRequestDTO currencyConversionRequestDTO) {
		String sourceCurrency = currencyConversionRequestDTO.getSourceCurrency();
		String targetCurrency = currencyConversionRequestDTO.getTargetCurrency();
		BigDecimal sourceAmount = currencyConversionRequestDTO.getAmount();
		String date = currencyConversionRequestDTO.getDate();

		CurrencyConversion currencyConversion = currencyService.getHistoricalCurrencyConversion(sourceCurrency, targetCurrency, sourceAmount, date);
		return ResponseEntity.status(HttpStatus.OK).body(
				new HistoricalCurrencyConversionResponseDTO(sourceCurrency, targetCurrency, sourceAmount, currencyConversion.getTargetAmount(), currencyConversion.getDate())
		);
	}

	@PostMapping("/bulk-currency-conversion")
	public ResponseEntity<List<CurrencyConversionResponseDTO>> getBulkCurrencyConversion(@NotNull @RequestParam("file") MultipartFile file) throws IOException {
		List<CurrencyConversionResponseDTO> responseDTOList = new ArrayList<>();
		currencyService.processFileAndGetCurrencyConversion(file).forEach(dto -> responseDTOList.add(
				new CurrencyConversionResponseDTO(dto.getSourceCurrency(), dto.getTargetCurrency(), dto.getSourceAmount(),
						dto.getTargetAmount(), dto.getTransactionId())
			)
		);

		return ResponseEntity.status(HttpStatus.OK).body(responseDTOList);
	}

}
