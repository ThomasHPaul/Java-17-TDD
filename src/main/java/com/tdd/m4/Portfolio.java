package com.tdd.m4;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

    List<Stock> stocks = new ArrayList<>();

    public double totalValue() {
        if(stocks.isEmpty()) {
            return 0;
        }
        return stocks.stream()
                .mapToDouble(Stock::totalValue)
                .sum();
    }

    public void add(Stock symbol) {
        stocks.add(symbol);
    }
}
