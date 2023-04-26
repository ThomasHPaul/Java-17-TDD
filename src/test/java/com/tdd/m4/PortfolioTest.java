package com.tdd.m4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.sound.sampled.Port;

public class PortfolioTest {

    @Test
    public void emptyPortfolio_hasZeroValue() {

        Portfolio portfolio = new Portfolio();
        Assertions.assertEquals(0, portfolio.totalValue());
    }

    @Test
    void portfolioWithOneStock_calculatesTotalValue() {

        int qty = 10;
        double px = 260;
        double value = qty * px;

        Portfolio portfolio = new Portfolio();
        portfolio.add(new Stock("MSFT", qty, px));
        Assertions.assertEquals(value, portfolio.totalValue());
    }

    @Test
    void portfolioWithMultipleStocks_calculatesTotalValue() {

        // Stock 1
        int microsoftQty = 10;
        double microsoftPx = 260;
        double microsoftValue = microsoftQty * microsoftPx;

        // Stock 2
        int applyQty = 1;
        double applyPx = 150;
        double appleValue = applyQty * applyPx;

        var portfolio = new Portfolio();
        portfolio.add(new Stock("MSFT", microsoftQty, microsoftPx));
        portfolio.add(new Stock("AAPL", applyQty, applyPx));

        Assertions.assertEquals(microsoftValue + appleValue, portfolio.totalValue());
    }

    @Test
    void portfolioWithAddedStockAtDifferentPrice_calculatesTotalValue() {

        // Stock 1
        int appleQty_1 = 10;
        double applePx_1 = 260;
        double appleValue_1 = appleQty_1 * applePx_1;

        // Stock 2
        int appleQty_2 = 1;
        double applePx_2 = 150;
        double appleValue_2 = appleQty_2 * applePx_2;

        var portfolio = new Portfolio();
        portfolio.add(new Stock("AAPL", appleQty_1, applePx_1));
        portfolio.add(new Stock("AAPL", appleQty_2, applePx_2));

        Assertions.assertEquals(appleValue_1 + appleValue_2, portfolio.totalValue());
    }
}
