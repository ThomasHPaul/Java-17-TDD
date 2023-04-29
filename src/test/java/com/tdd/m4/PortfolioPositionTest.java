package com.tdd.m4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class PortfolioPositionTest {

    private Portfolio portfolio = new Portfolio();
    private static final String microsoft = "MSFT";
    private static final String apple = "AAPL";
    private static final String oracle = "ORCL";

    @ParameterizedTest
    @MethodSource("portfolioPositionCountArgumentProvider")
    public void portfolioReturnsCorrectPositionCount(int size, Portfolio portfolio) {
        Assertions.assertEquals(size, portfolio.size());
    }

    private static Stream<Arguments> portfolioPositionCountArgumentProvider() {
        var portfolio1 = new Portfolio(); // 0 positions 0 buys
        var portfolio2 = new Portfolio(); // 1 position  1 buy
        var portfolio3 = new Portfolio(); // 3 positions 3 buys
        var portfolio4 = new Portfolio(); // 1 position  2 buys
        var portfolio5 = new Portfolio(); // 3 positions 4 buys

        var position1 = position(microsoft, 100, 250);
        var position2 = position(apple, 1, 80);
        var position3 = position(oracle, 10, 300);

        portfolio2.add(position1);
        portfolio3.add(position1);
        portfolio3.add(position2);
        portfolio3.add(position3);
        portfolio4.add(position1);
        portfolio4.add(position1);
        portfolio5.add(position1);
        portfolio5.add(position1);
        portfolio5.add(position2);
        portfolio5.add(position3);


        return Stream.of(
                Arguments.of(0, portfolio1),
                Arguments.of(1, portfolio2),
                Arguments.of(3, portfolio3),
                Arguments.of(1, portfolio4),
                Arguments.of(3, portfolio5)
                        );
    }

    @Test
    void positionAddedThenRemovedFromEmptyPortfolio_ReturnsZeroPositions() {
        var portfolio = new Portfolio();
        portfolio.add(position(microsoft, 10, 120));
        portfolio.remove(microsoft);

        Assertions.assertEquals(0, portfolio.size());
    }

    @Test
    void positionPartiallySold_PositionStillInPortfolio() {

        var portfolio = new Portfolio();
        portfolio.add(position(microsoft, 10, 250));
        portfolio.sell(microsoft, 5);

        Assertions.assertTrue(portfolio.containsPosition(microsoft));
    }

    @Test
    void positionPartiallySold_PositionValueUpdated() {

        var portfolio = new Portfolio();
        portfolio.add(position(microsoft, 10, 250));
        portfolio.sell(microsoft, 5);

        Assertions.assertEquals(1250, portfolio.getPosition(microsoft).getValue());
    }

    @Test
    void positionPartiallySold_PositionHasCorrectQuantityRemaining() {

        var portfolio = new Portfolio();
        portfolio.add(position(microsoft, 10, 250));
        portfolio.sell(microsoft, 5);

        Assertions.assertEquals(5, portfolio.getPosition(microsoft).getQty());
    }

    // TODO Introduce Money API, does rounding api & understands currency - jsr 354 - Money and Currency API

    @Test
    void portfolioWithOnePosition_ReturnsThatPosition() {

        var portfolio = new Portfolio();

        String symbol = microsoft;

        portfolio.add(position(microsoft, 10, 260));
        Assertions.assertEquals(1, portfolio.size());

        Assertions.assertEquals(10, portfolio.getPosition(symbol).getQty());
        Assertions.assertEquals(260, portfolio.getPosition(symbol).getPx());
        Assertions.assertEquals(2600, portfolio.getPosition(symbol).getValue());
    }

    @Test
    public void portfolioWithTwoDifferentPositions_ReturnsThosePositions() {
        var portfolio = new Portfolio();


        String apple = "AAPL";

        portfolio.add(position(microsoft, 10, 260));
        portfolio.add(position("AAPL", 2, 150));

        Assertions.assertEquals(2, portfolio.size());

        // msft
        var microsoftPosition = portfolio.getPosition(microsoft);
        Assertions.assertEquals(10, microsoftPosition.getQty());
        Assertions.assertEquals(260, microsoftPosition.getPx());
        Assertions.assertEquals(2600, microsoftPosition.getValue());

        // aapl
        var applePosition = portfolio.getPosition(apple);
        Assertions.assertEquals(2, applePosition.getQty());
        Assertions.assertEquals(150, applePosition.getPx());
        Assertions.assertEquals(300, applePosition.getValue());
    }

    @Test
    public void portfolioWithSameStock_ReturnsOnePosition()  {
        var portfolio = new Portfolio();



        portfolio.add(position(microsoft, 10, 260));
        portfolio.add(position(microsoft, 5, 200));

        Assertions.assertEquals(1, portfolio.size());
    }

    @Test
    public void portfolioWithSameStock_ReturnsCorrectQty()  {
        var portfolio = new Portfolio();



        portfolio.add(position(microsoft, 10, 260));
        portfolio.add(position(microsoft, 1, 200));

        Assertions.assertEquals(11, portfolio.getPosition(microsoft).getQty());
    }

    @Test
    public void portfolioWithSameStock_ReturnsCorrectAveragePrice() {
        var portfolio = new Portfolio();



        portfolio.add(position(microsoft, 1, 240));
        portfolio.add(position(microsoft, 1, 220));

        Assertions.assertEquals(230, portfolio.getPosition(microsoft).getAveragePx());
    }

    @Test
    public void portfolioWithSameStock_ReturnsCorrectPositionValue() {
        var portfolio = new Portfolio();


        portfolio.add(position(microsoft, 2, 240));
        portfolio.add(position(microsoft, 1, 220));

        double expected = 2 * 240 + 220;
        Assertions.assertEquals(expected, portfolio.getPosition(microsoft).getValue());
    }

    @Test
    public void complexPortfolio_ReturnsCorrectTotalValue() {
        var portfolio = new Portfolio();

        portfolio.add(position(microsoft, 1, 260));
        portfolio.add(position(microsoft, 2, 250));

        portfolio.add(position("AAPL", 5, 90));
        portfolio.add(position("AAPL", 10, 80));

        portfolio.add(position("ORCL", 100, 80));

        Assertions.assertEquals(3, portfolio.size());
        Assertions.assertEquals(10010, portfolio.getTotalValue());
    }



    private static Position position(String symbol, int qty, double px) {
        return new Position(new Stock(symbol), qty, px);
    }
}
