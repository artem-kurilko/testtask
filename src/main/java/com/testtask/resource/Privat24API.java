package com.testtask.resource;

import com.testtask.model.Rate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import static com.testtask.resource.utils.RequestProcessor.sendRequest;

public class Privat24API implements ExchangeAPI {

    @Override
    public List<Rate> getCurrencies() {
        final String url = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=11";
        JSONArray response = new JSONArray(sendRequest(url));
        List<Rate> rates = new LinkedList<>();
        for (Object obj : response) {
            JSONObject cur = (JSONObject) obj;
            Rate rate = new Rate(cur.getFloat("buy"), cur.getString("ccy"));
            rates.add(rate);
        }
        return rates;
    }
}
