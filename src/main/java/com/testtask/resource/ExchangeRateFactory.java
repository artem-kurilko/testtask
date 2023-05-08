package com.testtask.resource;

import com.testtask.model.Exchange;
import com.testtask.model.Rate;
import org.json.JSONArray;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class ExchangeRateFactory {

    public static JSONArray getAllCurrencies(String startDay, String toDate) {
        return null;
    }

    public static List<Exchange> getAllCurrencies() {
        MonobankAPI monobankAPI = new MonobankAPI();
        GovbankAPI govbankAPI = new GovbankAPI();
        Privat24API privat24API = new Privat24API();

        List<Rate> monoRates = monobankAPI.getCurrencies();
        List<Rate> govbankRates = govbankAPI.getCurrencies();
        List<Rate> privat24Rates = privat24API.getCurrencies();

        return List.of(new Exchange("monobank", monoRates),
                new Exchange("govbank", govbankRates),
                new Exchange("privat24", privat24Rates));
    }

    public static void main(String[] args) {
        long date = 1683324074;
        Timestamp timestamp = new Timestamp(date);
        Date dat = new Date(date * 1000);
        System.out.println(dat);
    }
}
