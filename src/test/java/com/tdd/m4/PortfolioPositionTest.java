package com.tdd.m4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;



public class PortfolioPositionTest {

    private final Portfolio portfolio = new Portfolio();
    private static final String microsoft = "MSFT";
    private static final String apple = "AAPL";
    private static final String oracle = "ORCL";

    @ParameterizedTest
    @MethodSource("portfolioPositionCountArgumentProvider")
    public void portfolioReturnsCorrectPositionCount(int size, Portfolio portfolio) {
        assertEquals(size, portfolio.size());
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
    void positionPartiallySold_PositionStillInPortfolio() {

        portfolio.add(position(microsoft, 10, 250));
        portfolio.sell(microsoft,5);

        assertTrue(portfolio.containsPosition(microsoft));
    }

    @Test
    void positionPartiallySold_PositionValueUpdated() {

        portfolio.add(position(microsoft, 10, 250));
        portfolio.sell(microsoft,5);

        assertEquals(1250, portfolio.getPosition(microsoft).getValue());
    }

    @Test
    void positionPartiallySold_PositionHasCorrectQuantityRemaining() {

        portfolio.add(position(microsoft, 10, 250));
        portfolio.sell(microsoft,5);

        assertEquals(5, portfolio.getPosition(microsoft).getQty());
    }

    @Test
    void positionSellZero_PositionUnchanged() {
        portfolio.add(position(microsoft, 10, 250));
        portfolio.sell(microsoft,0);

        assertEquals(10, portfolio.getPosition(microsoft).getQty());
    }

    @Test
    void positionOversell_PositionThrowsError() {
        portfolio.add(position(microsoft, 10, 250));

        assertThrows(ArithmeticException.class,
                () -> portfolio.sell(microsoft,100),
                "Cannot sell more shares than in position");
    }

    @Test
    void positionSellNegativeAmount_PositionThrowsError() {
        portfolio.add(position(microsoft, 10, 250));

        assertThrows(ArithmeticException.class,
                () -> portfolio.sell(microsoft,-1),
                "Cannot sell negative shares");
    }

    @Test
    void positionSellAllShares_PositionRemovedFromPortfolio() {
        portfolio.add(position(microsoft, 10, 250));
        portfolio.sell(microsoft, 10);
        assertEquals(0, portfolio.size());
    }

    // TODO Introduce Money API, does rounding api & understands currency - jsr 354 - Money and Currency API

    @Test
    void portfolioWithOnePosition_ReturnsThatPosition() {

        String symbol = microsoft;

        portfolio.add(position(microsoft, 10, 260));
        assertEquals(1, portfolio.size());

        assertEquals(10, portfolio.getPosition(symbol).getQty());
        assertEquals(260, portfolio.getPosition(symbol).getPx());
        assertEquals(2600, portfolio.getPosition(symbol).getValue());
    }

    @Test
    public void portfolioWithTwoDifferentPositions_ReturnsThosePositions() {

        portfolio.add(position(microsoft, 10, 260));
        portfolio.add(position(apple, 2, 150));

        assertEquals(2, portfolio.size());

        // msft
        var microsoftPosition = portfolio.getPosition(microsoft);
        assertEquals(10, microsoftPosition.getQty());
        assertEquals(260, microsoftPosition.getPx());
        assertEquals(2600, microsoftPosition.getValue());

        // aapl
        var applePosition = portfolio.getPosition(apple);
        assertEquals(2, applePosition.getQty());
        assertEquals(150, applePosition.getPx());
        assertEquals(300, applePosition.getValue());
    }

    @Test
    public void portfolioWithSameStock_ReturnsOnePosition()  {

        portfolio.add(position(microsoft, 10, 260));
        portfolio.add(position(microsoft, 5, 200));

        assertEquals(1, portfolio.size());
    }

    @Test
    public void portfolioWithSameStock_ReturnsCorrectQty()  {

        portfolio.add(position(microsoft, 10, 260));
        portfolio.add(position(microsoft, 1, 200));

        assertEquals(11, portfolio.getPosition(microsoft).getQty());
    }

    @Test
    public void portfolioWithSameStock_ReturnsCorrectAveragePrice() {

        portfolio.add(position(microsoft, 1, 240));
        portfolio.add(position(microsoft, 1, 220));

        assertEquals(230, portfolio.getPosition(microsoft).getAveragePx());
    }

    @Test
    public void portfolioWithSameStock_ReturnsCorrectPositionValue() {

        portfolio.add(position(microsoft, 2, 240));
        portfolio.add(position(microsoft, 1, 220));

        double expected = 2 * 240 + 220;
        assertEquals(expected, portfolio.getPosition(microsoft).getValue());
    }

    @Test
    public void complexPortfolio_ReturnsCorrectTotalValue() {

        portfolio.add(position(microsoft, 1, 260));
        portfolio.add(position(microsoft, 2, 250));

        portfolio.add(position(apple, 5, 90));
        portfolio.add(position(apple, 10, 80));
        portfolio.add(position(oracle, 100, 80));

        assertEquals(3, portfolio.size());
        assertEquals(10010, portfolio.getTotalValue());
    }



    private static Position position(String symbol, int qty, double px) {
        return new Position(new Stock(symbol), qty, px);
    }
}
