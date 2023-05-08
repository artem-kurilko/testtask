package com.testtask.resource;

import com.testtask.model.Rate;
import com.testtask.service.RateService;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static com.testtask.model.BankName.privat24;
import static com.testtask.model.Currency.EUR;
import static com.testtask.model.Currency.USD;
import static com.testtask.resource.utils.RequestProcessor.sendRequest;
import static com.testtask.resource.utils.UpdateRateUtils.isRateUpdated;

@Component
public class Privat24API implements ExchangeAPI {
    private static final String pattern = "dd.MM.yyyy";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    private static final int MAX_AMOUNT_OF_DAYS_TO_STORE = 31;
    private final RateService rateService;

    @Autowired
    public Privat24API(RateService rateService) {
        this.rateService = rateService;
    }

    @Override
    public List<Rate> getRates() {
        return rateService.findRatesByExchange(privat24);
    }

    @SneakyThrows
    @Override
    public void updateRates() {
        String url = "https://api.privatbank.ua/p24api/exchange_rates?json&date=";
        List<Rate> usdRates = rateService.findRatesByNameAndExchange(USD, privat24);
        List<Rate> eurRates = rateService.findRatesByNameAndExchange(EUR, privat24);

        for (int i = MAX_AMOUNT_OF_DAYS_TO_STORE; i > 0; i--) {
            String link = url + getDateParameter(i);
            JSONObject response = new JSONObject(sendRequest(link));
            JSONArray responseRates = response.getJSONArray("exchangeRate");
            boolean usdUpdated=false, eurUpdated=false;
            for (int n = 0; n < responseRates.length(); n++) {
                JSONObject cur = responseRates.getJSONObject(n);
                if (cur.getString("currency").equals(USD.name())){
                    Date date = sdf.parse(getDateParameter(i));
                    float price = cur.getFloat("purchaseRateNB");
                    usdUpdated = isRateUpdated(usdRates, date, price, USD, privat24);
                } else if (cur.getString("currency").equals(EUR.name())) {
                    Date date = sdf.parse(getDateParameter(i));
                    float price = cur.getFloat("purchaseRateNB");
                    eurUpdated = isRateUpdated(eurRates, date, price, EUR, privat24);
                }
                if (usdUpdated && eurUpdated)
                    break;
            }
        }
    }

    private static String getDateParameter(int minusDaysCount) {
        LocalDate ld = LocalDate.now().minusDays(minusDaysCount);
        Date startDate = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return sdf.format(startDate);
    }

}
