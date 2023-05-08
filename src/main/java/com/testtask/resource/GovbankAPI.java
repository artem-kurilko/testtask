package com.testtask.resource;

import com.testtask.model.Rate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

import static com.testtask.resource.utils.RequestProcessor.sendRequest;

@Component
public class GovbankAPI implements ExchangeAPI {

    @Override
    public List<Rate> getCurrencies() {
        final String url = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchangenew?json";
        JSONArray allCurrencies = new JSONArray(sendRequest(url));
        List<Rate> rates = new LinkedList<>();
        Rate eurRate = null, usdRate = null;
        for (int i = 0; i < allCurrencies.length(); i++) {
            JSONObject cur = allCurrencies.getJSONObject(i);
            if (cur.getString("cc").equals("USD")) {
                usdRate = new Rate(cur.getFloat("rate"), "USD");
            } else if (cur.getString("cc").equals("EUR")) {
                eurRate = new Rate(cur.getFloat("rate"), "EUR");
            }
            if (eurRate != null && usdRate != null)
                break;
        }
        rates.add(eurRate);
        rates.add(usdRate);
        return rates;
    }
}
