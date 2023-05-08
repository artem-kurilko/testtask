package com.testtask.resource;

import com.testtask.model.Currency;
import com.testtask.model.Rate;
import com.testtask.service.RateService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static com.testtask.model.BankName.mono;
import static com.testtask.model.Currency.EUR;
import static com.testtask.model.Currency.USD;
import static com.testtask.resource.utils.RequestProcessor.sendRequest;

@Component
public class MonobankAPI implements ExchangeAPI{
    private final RateService rateService;

    @Autowired
    public MonobankAPI(RateService rateService) {
        this.rateService = rateService;
    }

    @Override
    public List<Rate> getRates() {
        return rateService.findRatesByExchange(mono);
    }

    @Override
    public void updateRates() {
        final String url = "https://api.monobank.ua/bank/currency";
        final int USD_CODE = 840;
        final int EUR_CODE = 978;
        JSONArray response = new JSONArray(sendRequest(url));
        List<Rate> usdRates = rateService.findRatesByNameAndExchange(USD, mono);
        List<Rate> eurRates = rateService.findRatesByNameAndExchange(EUR, mono);
        boolean usdUpdated = false, eurUpdated = false;

        for (int i = 0; i < response.length(); i++) {
            JSONObject cur = response.getJSONObject(i);

            if (cur.getInt("currencyCodeA") == USD_CODE) {
                usdUpdated = isRateUpdated(usdRates, cur, USD);
            } else if (cur.getInt("currencyCodeA") == EUR_CODE) {
                eurUpdated = isRateUpdated(eurRates, cur, EUR);
            }
            if (usdUpdated && eurUpdated)
                break;
        }
    }

    private boolean isRateUpdated(List<Rate> rates, JSONObject cur, Currency currency) {
        Timestamp ts = new Timestamp(cur.getLong("date"));
        Date date = new Date(ts.getTime());
        Rate dbRate = rates.stream().filter(rate -> rate.getDate().equals(date)).findAny().orElse(null);

        if (dbRate == null) {
            float price = cur.getFloat("rateBuy");
            Rate rate = Rate.builder()
                    .price(price)
                    .symbol(currency)
                    .date(date)
                    .bankName(mono)
                    .build();
            rateService.saveRate(rate);
        } return true;
    }
}
