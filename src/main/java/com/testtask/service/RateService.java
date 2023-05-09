package com.testtask.service;

import com.testtask.model.BankName;
import com.testtask.model.Currency;
import com.testtask.model.Rate;
import com.testtask.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public List<Rate> findRatesByExchange(BankName bankName) {
        return rateRepository.findAllByBankName(bankName);
    }

    public List<Rate> findRatesByNameAndExchange(Currency currency, BankName exchange) {
        return rateRepository.findAllBySymbolAndBankName(currency, exchange);
    }

    public List<Rate> findRatesByDateAndExchange(Date date, BankName bankName) {
        return rateRepository.findRatesByDateAndBankName(date, bankName);
    }
}
