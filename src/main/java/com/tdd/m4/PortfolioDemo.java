package com.tdd.m4;

public class PortfolioDemo {

    public static void main(String[] args) {

        var portfolio = new Portfolio();

        portfolio.add(position("MSFT", 1, 260));
        portfolio.add(position("MSFT", 2, 250));
        portfolio.add(position("AAPL", 5, 80));
        portfolio.add(position("AAPL", 10, 80));
        portfolio.add(position("ORCL", 100, 80));

        portfolio.print();
    }

    private static Position position(String symbol, int qty, double px) {
        return new Position(new Stock(symbol), qty, px);
    }
}
