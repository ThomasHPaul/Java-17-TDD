package com.tdd.m4;

public class Stock {

    String symbol;
    int qty;
    double px;

    public Stock(String symbol, int qty, double px) {
        this.symbol = symbol;
        this.qty = qty;
        this.px = px;
    }

    public String getSymbol() { return symbol; }

    public int getQty() { return qty; }

    public double getPx() { return px; }

    public double totalValue() {
        return qty * px;
    }

    @Override
    public String toString() {
        return String.format("{ %s | Qty: %s | Px: %s | Value: %s}", symbol, qty, px, qty * px);
    }
}
