package com.testtask.resource;

import com.testtask.model.Exchange;
import com.testtask.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class ExchangeRateFactory {
    private static final String pattern = "dd.MM.yyyy";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    private final MonobankAPI monobankAPI;
    private final GovbankAPI govbankAPI;
    private final Privat24API privat24API;

    @Autowired
    public ExchangeRateFactory(MonobankAPI monobankAPI, GovbankAPI govbankAPI, Privat24API privat24API) {
        this.monobankAPI = monobankAPI;
        this.govbankAPI = govbankAPI;
        this.privat24API = privat24API;
    }

    public List<Exchange> getCurrentRates() {
        LocalDate ld = LocalDate.now();
        Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Rate> monoRates = monobankAPI.getTodayRates(date);
        List<Rate> govbankRates = govbankAPI.getTodayRates(date);
        List<Rate> privat24Rates = privat24API.getTodayRates(date);

        return List.of(new Exchange("monobank", monoRates),
                new Exchange("govbank", govbankRates),
                new Exchange("privat24", privat24Rates));
    }

    public List<Exchange> getRatesByDate(String startDate, String finishDate) throws ParseException {
        Date start = sdf.parse(startDate);
        Date finish = sdf.parse(finishDate);

        // we store date in yyyy-mm-dd

        return null;
    }

}
