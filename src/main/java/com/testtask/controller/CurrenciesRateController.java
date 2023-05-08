package com.testtask.controller;

import com.testtask.model.Exchange;
import com.testtask.resource.ExchangeRateFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CurrenciesRateController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Exchange>> getAvgRates() {
        List<Exchange> currenciesRates = ExchangeRateFactory.getAllCurrencies();
        return new ResponseEntity<>(currenciesRates, HttpStatus.OK);
    }

   /* @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAvgRates(@RequestParam(value="startDate")@DateTimeFormat(pattern="MMddyyyy") Date startDate,
                                              @RequestParam(value="toDate")@DateTimeFormat(pattern="MMddyyyy") Date toDate) {

        return new ResponseEntity<>(null, HttpStatus.OK);
    }*/

}
