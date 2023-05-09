package com.testtask.resource.utils;

import com.testtask.model.BankName;
import com.testtask.model.Currency;
import com.testtask.model.Rate;
import com.testtask.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(date);
        Rate dbRate = rates.stream().filter(rate -> rate.getDate().equals(dateStr)).findAny().orElse(null);

        if (dbRate == null) {
            Rate rate = Rate.builder()
                    .price(price)
                    .symbol(currency)
                    .date(dateStr)
                    .bankName(bankName)
                    .build();
            rateService.saveRate(rate);
        } return true;
    }
}
