package com.testtask.resource;

import com.testtask.model.Rate;
import com.testtask.service.RateService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static com.testtask.model.BankName.mono;
import static com.testtask.model.Currency.EUR;
import static com.testtask.model.Currency.USD;
import static com.testtask.resource.utils.RequestProcessor.sendRequest;
import static com.testtask.resource.utils.UpdateRateUtils.isRateUpdated;

@Component
public class MonobankAPI implements ExchangeAPI{
    private final RateService rateService;

    @Autowired
    public MonobankAPI(RateService rateService) {
        this.rateService = rateService;
    }

    @Override
    public List<Rate> getTodayRates(Date date) {
        return rateService.findRatesByDateAndExchange(date, mono);
    }

    @Override
    public void updateRates() {
        final String url = "https://api.monobank.ua/bank/currency";
        final int USD_CODE = 840, EUR_CODE = 978, HRV_CODE = 980;
        JSONArray response = new JSONArray(sendRequest(url));
        List<Rate> usdRates = rateService.findRatesByNameAndExchange(USD, mono);
        List<Rate> eurRates = rateService.findRatesByNameAndExchange(EUR, mono);
        boolean usdUpdated = false, eurUpdated = false;

        for (int i = 0; i < response.length(); i++) {
            JSONObject cur = response.getJSONObject(i);
            if (cur.getInt("currencyCodeA") == USD_CODE && cur.getInt("currencyCodeB") == HRV_CODE) {
                long ts = cur.getLong("date") * 1000;
                Date date = new Date(ts);
                float price = cur.getFloat("rateBuy");
                usdUpdated = isRateUpdated(usdRates, date, price, USD, mono);
            } else if (cur.getInt("currencyCodeA") == EUR_CODE && cur.getInt("currencyCodeB") == HRV_CODE) {
                long ts = cur.getLong("date") * 1000;
                Date date = new Date(ts);
                float price = cur.getFloat("rateBuy");
                eurUpdated = isRateUpdated(eurRates, date, price, EUR, mono);
            }
            if (usdUpdated && eurUpdated)
                break;
        }
    }

}
