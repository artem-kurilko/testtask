package com.testtask.resource.utils;

import com.testtask.model.BankName;
import com.testtask.model.Currency;
import com.testtask.model.Rate;
import com.testtask.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class UpdateRateUtils {
    private static RateService rateService;

    @Autowired
    public UpdateRateUtils(RateService rateService) {
        UpdateRateUtils.rateService = rateService;
    }

    public static boolean isRateUpdated(List<Rate> rates, Date date, float price, Currency currency, BankName bankName) {
        Rate dbRate = rates.stream().filter(rate -> rate.getDate().equals(date)).findAny().orElse(null);

        if (dbRate == null) {
            Rate rate = Rate.builder()
                    .price(price)
                    .symbol(currency)
                    .date(date)
                    .bankName(bankName)
                    .build();
            rateService.saveRate(rate);
        } return true;
    }
}
