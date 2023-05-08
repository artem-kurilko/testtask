package com.testtask.controller;

import com.testtask.model.Exchange;
import com.testtask.resource.ExchangeRateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CurrenciesRateController {
    private final ExchangeRateFactory exchangeRateFactory;

    @Autowired
    public CurrenciesRateController(ExchangeRateFactory exchangeRateFactory) {
        this.exchangeRateFactory = exchangeRateFactory;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Exchange>> getAvgRates() {
        List<Exchange> currenciesRates = exchangeRateFactory.getAllCurrencies();
        return new ResponseEntity<>(currenciesRates, HttpStatus.OK);
    }

}
