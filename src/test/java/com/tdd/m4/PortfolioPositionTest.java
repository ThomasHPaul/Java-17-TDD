package com.tdd.m4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PortfolioPositionTest {

    @Test
    public void emptyPortfolio_zeroPositions() {

        var Portfolio = new Portfolio();
        Assertions.assertEquals(0, portfolio.getAllPositions().size());
    }
}
