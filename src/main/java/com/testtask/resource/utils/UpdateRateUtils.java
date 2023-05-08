package com.testtask.resource.utils;

import com.testtask.model.Currency;
import com.testtask.model.Rate;
import com.testtask.service.RateService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static com.testtask.model.BankName.mono;

@Component
public class UpdateRateUtils {
    private static RateService rateService;

    @Autowired
    public UpdateRateUtils(RateService rateService) {
        UpdateRateUtils.rateService = rateService;
    }

    public static boolean isRateUpdated(List<Rate> rates, Date date, float price, Currency currency) {
        Rate dbRate = rates.stream().filter(rate -> rate.getDate().equals(date)).findAny().orElse(null);

        if (dbRate == null) {
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
