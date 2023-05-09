package com.testtask.resource;

import com.testtask.model.Currency;
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

import static com.testtask.model.BankName.govbank;
import static com.testtask.model.Currency.EUR;
import static com.testtask.model.Currency.USD;
import static com.testtask.resource.utils.RequestProcessor.sendRequest;
import static com.testtask.resource.utils.UpdateRateUtils.isRateUpdated;

@Component
public class GovbankAPI implements ExchangeAPI {
    private static final String pattern = "yyyyMMdd";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    private static final int MAX_AMOUNT_OF_DAYS_TO_STORE = 31;
    private final RateService rateService;

    @Autowired
    public GovbankAPI(RateService rateService) {
        this.rateService = rateService;
    }

    @Override
    public List<Rate> getTodayRates(Date date) {
        return rateService.findRatesByDateAndExchange(date, govbank);
    }

    @Override
    public List<Rate> getRates() {
        return rateService.findRatesByExchange(govbank);
    }

    @Override
    public void updateRates() {
        List<Rate> usdRates = rateService.findRatesByNameAndExchange(USD, govbank);
        updateRatesBySymbol(USD, usdRates);

        List<Rate> eurRates = rateService.findRatesByNameAndExchange(EUR, govbank);
        updateRatesBySymbol(EUR, eurRates);
    }

    @SneakyThrows
    private void updateRatesBySymbol(Currency currency, List<Rate> rates) {
        String startDate = getStartDate();
        String finishDate = sdf.format(new Date());
        String url = String.format(
                "https://bank.gov.ua/NBU_Exchange/exchange_site?start=%s&end=%s&valcode=%s&sort=exchangedate&json", startDate, finishDate, currency.name().toLowerCase());
        JSONArray response = new JSONArray(sendRequest(url));
        final String govPattern = "dd.MM.yyyy";
        final SimpleDateFormat formatter = new SimpleDateFormat(govPattern);

        for (int i = 0; i < response.length(); i++) {
            JSONObject cur = response.getJSONObject(i);
            String exchangeDate = cur.getString("exchangedate");
            Date date = formatter.parse(exchangeDate);
            float price = cur.getFloat("rate");
            isRateUpdated(rates, date, price, currency, govbank);
        }
    }

    private static String getStartDate() {
        LocalDate ld = LocalDate.now().minusDays(MAX_AMOUNT_OF_DAYS_TO_STORE);
        Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return sdf.format(date);
    }
}
