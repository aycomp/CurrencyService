package com.ao.CurrencyExhange.controller.exception;

import com.ao.CurrencyExhange.client.exception.CurrencyLayerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
		return buildResponseEntity(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage());
	}

	@ExceptionHandler(CurrencyLayerException.class)
	public ResponseEntity<Object> handleCurrencyLayerException(CurrencyLayerException ex) {
		return buildResponseEntity(HttpStatus.BAD_GATEWAY, "Currency Layer Error", ex.getMessage());
	}

	private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String error, String message) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", status.value());
		body.put("error", error);
		body.put("message", message);
		return new ResponseEntity<>(body, status);
	}
}
