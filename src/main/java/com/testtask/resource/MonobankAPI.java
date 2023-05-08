package com.testtask.resource;

import com.testtask.model.Rate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import static com.testtask.resource.utils.RequestProcessor.sendRequest;

public class MonobankAPI implements ExchangeAPI{

    @Override
    public List<Rate> getCurrencies() {
        final String url = "https://api.monobank.ua/bank/currency";
        final int USD_CODE = 840;
        final int EUR_CODE = 978;
        Rate usdRate = null, eurRate = null;
        JSONArray response = new JSONArray(sendRequest(url));
        List<Rate> rates = new LinkedList<>();
        for (int i = 0; i < response.length(); i++) {
            JSONObject cur = response.getJSONObject(i);
            if (cur.getInt("currencyCodeA") == USD_CODE) {
                usdRate = new Rate(cur.getFloat("rateBuy"), "USD");
            } else if (cur.getInt("currencyCodeA") == EUR_CODE) {
                eurRate = new Rate(cur.getFloat("rateBuy"), "EUR");
            }
            if (eurRate != null && usdRate != null)
                break;
        }
        rates.add(eurRate);
        rates.add(usdRate);
        return rates;
    }
}
