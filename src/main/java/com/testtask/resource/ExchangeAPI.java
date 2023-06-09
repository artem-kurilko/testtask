package com.testtask.resource;

import com.testtask.model.Rate;

import java.util.Date;
import java.util.List;

public interface ExchangeAPI {

    List<Rate> getTodayRates(Date date);

    List<Rate> getRatesByDate(Date startDate, Date finishDate);

    void updateRates();

}
