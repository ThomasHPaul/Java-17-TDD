package com.tdd.m4;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

    List<Stock> stocks = new ArrayList<>();

    public void printPortfolio() {
        stocks.forEach(System.out::println);
    }

    public double totalValue() {
        if(stocks.isEmpty()) {
            return 0;
        }
        return stocks.stream()
                .mapToDouble(Stock::totalValue)
                .sum();
    }

    public void add(Stock symbol) {
        if(containsSymbol(symbol)) {
            int locationInStocks = getLocationInStocks(symbol.getSymbol());
            Stock currentStock = stocks.get(locationInStocks);

            String ticker = currentStock.getSymbol();
            int newQty = symbol.getQty() + currentStock.getQty();
            double price = ( currentStock.getPx() + symbol.getPx() ) / newQty; // get average of the 2 prices


            Stock updatedStock = new Stock(ticker, newQty, price);
            stocks.set(locationInStocks, updatedStock);
        } else {
            stocks.add(symbol);
        }
    }

    private boolean containsSymbol(Stock symbol) {
        for(Stock stockInPortfolio : stocks) {
            if(stockInPortfolio.getSymbol().equals(symbol.getSymbol())) {
                return true;
            }
        }
        return false;
    }

    private int getLocationInStocks(String ticker) {
        for(int i = 0; i < stocks.size(); i++) {
            if( stocks.get(i).getSymbol().equals(ticker) ) {
                return i;
            }
        }
        return -1;
    }
}
