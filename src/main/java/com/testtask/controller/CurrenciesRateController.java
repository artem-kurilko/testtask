package com.testtask.controller;

import com.testtask.model.Exchange;
import com.testtask.resource.ExchangeRateFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
public class CurrenciesRateController {
    private final ExchangeRateFactory exchangeRateFactory;

    @Autowired
    public CurrenciesRateController(ExchangeRateFactory exchangeRateFactory) {
        this.exchangeRateFactory = exchangeRateFactory;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Exchange>> getAvgRates() {
        List<Exchange> currenciesRates = exchangeRateFactory.getCurrentRates();
        return new ResponseEntity<>(currenciesRates, HttpStatus.OK);
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Exchange>> getAvgRatesByDate(@RequestParam String startDate, @RequestParam String finishDate) throws ParseException {
        List<Exchange> currenciesRates = exchangeRateFactory.getRatesByDate(startDate, finishDate);
        return new ResponseEntity<>(currenciesRates, HttpStatus.OK);
    }

}
