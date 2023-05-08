package com.testtask.resource;

import com.testtask.model.Exchange;
import com.testtask.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeRateFactory {
    private final MonobankAPI monobankAPI;
    private final GovbankAPI govbankAPI;
    private final Privat24API privat24API;

    @Autowired
    public ExchangeRateFactory(MonobankAPI monobankAPI, GovbankAPI govbankAPI, Privat24API privat24API) {
        this.monobankAPI = monobankAPI;
        this.govbankAPI = govbankAPI;
        this.privat24API = privat24API;
    }

    public List<Exchange> getAllCurrencies() {
        List<Rate> monoRates = monobankAPI.getRates();
        List<Rate> govbankRates = govbankAPI.getRates();
        List<Rate> privat24Rates = privat24API.getRates();

        return List.of(new Exchange("monobank", monoRates),
                new Exchange("govbank", govbankRates),
                new Exchange("privat24", privat24Rates));
    }
}
