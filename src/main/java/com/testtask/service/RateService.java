package com.testtask.service;

import com.testtask.model.BankName;
import com.testtask.model.Currency;
import com.testtask.model.Rate;
import com.testtask.repository.RateRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class RateService {
    private final RateRepository rateRepository;

    @Autowired
    public RateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public void saveRate(Rate rate) {
        rateRepository.save(rate);
    }

    public List<Rate> findRatesByNameAndExchange(Currency currency, BankName exchange) {
        return rateRepository.findAllBySymbolAndBankName(currency, exchange);
    }

    public List<Rate> findRatesByDateAndExchange(Date date, BankName bankName) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(date);
        return rateRepository.findRatesByDateAndBankName(dateStr, bankName);
    }

    @SneakyThrows
    public List<Rate> findRatedByPeriodAndExchange(Date startDate, Date finishDate, BankName bankName) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        List<Rate> rates = rateRepository.findAllByBankName(bankName);
        List<Rate> filteredRates = new LinkedList<>();

        for (Rate rate : rates) {
            Date date = df.parse(rate.getDate());
            if (startDate.compareTo(date) == 0 || finishDate.compareTo(date) == 0 ||
                    (startDate.compareTo(date) < 0 && finishDate.compareTo(date) > 0)) {
                System.out.println("Exists");
                filteredRates.add(rate);
            }
        }
        return filteredRates;
    }

}
