package com.testtask.repository;

import com.testtask.model.BankName;
import com.testtask.model.Currency;
import com.testtask.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, String> {

    List<Rate> findAllByBankName(BankName bank);

    List<Rate> findAllBySymbolAndBankName(Currency currency, BankName bankName);

    List<Rate> findRatesByDateAndBankName(String date, BankName bankName);

}
