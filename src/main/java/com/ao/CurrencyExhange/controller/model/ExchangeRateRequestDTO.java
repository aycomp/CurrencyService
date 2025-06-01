package com.ao.CurrencyExhange.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateRequestDTO extends CurrencyRequestBaseDTO{

}
