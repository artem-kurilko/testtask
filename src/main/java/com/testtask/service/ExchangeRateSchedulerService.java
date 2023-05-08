package com.testtask.service;

import com.testtask.resource.GovbankAPI;
import com.testtask.resource.MonobankAPI;
import com.testtask.resource.Privat24API;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExchangeRateSchedulerService {
    private final MonobankAPI monobankAPI;
    private final GovbankAPI govbankAPI;
    private final Privat24API privat24API;

    @Autowired
    public ExchangeRateSchedulerService(MonobankAPI monobankAPI, GovbankAPI govbankAPI, Privat24API privat24API) {
        this.monobankAPI = monobankAPI;
        this.govbankAPI = govbankAPI;
        this.privat24API = privat24API;
    }

    @Scheduled(cron = "0 0 */1 * * *") //every hour
    public void scheduleExchangeRateUpdate() {
        log.info("Updating database...");
        monobankAPI.updateRates();
        govbankAPI.updateRates();
        privat24API.updateRates();
    }
}
