package com.testtask.model;

import java.util.List;

public class Exchange {
    public String name;
    public List<Rate> rates;

    public Exchange(String name, List<Rate> rates) {
        this.name = name;
        this.rates = rates;
    }
}
