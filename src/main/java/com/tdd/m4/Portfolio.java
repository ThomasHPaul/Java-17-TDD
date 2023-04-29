package com.tdd.m4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio {

    private final Map<String, Position> positions;

    public Portfolio() {
        positions = new HashMap<>();
    }

    public Map<String, Position> getAllPositions() {
        return positions;
    }

    public void add(Position position) {
        String symbol = position.getStock().symbol();

        if(positions.containsKey(symbol)) {
            Position existingPosition = positions.get(symbol);
            int newQuantity = existingPosition.getQty() + position.getQty();
            double newAveragePrice = (existingPosition.getQty() * existingPosition.getAveragePx()
                    + position.getQty() * position.getAveragePx()) / newQuantity;

            existingPosition.setQuantity(newQuantity);
            existingPosition.setAveragePrice(newAveragePrice);
        } else {
            positions.put(symbol, position);
        }
    }

    public double getTotalValue() {
        return positions.values().stream()
                .mapToDouble(Position::getValue)
                .sum();
    }

    public Position getPosition(String symbol) {
        return positions.get(symbol);
    }

    public void print() {
        positions.values().forEach(System.out::println);

        System.out.println("=========================");
        System.out.println(getTotalValue());
    }

    public void remove(String symbol) {
        positions.remove(symbol);
    }

    public void sell(String symbol, int qtyToSell) {
        var position = getPosition(symbol);
        position.setQuantity(position.getQty() - qtyToSell);
    }

    public boolean containsPosition(String symbol) {
        return positions.containsKey(symbol);
    }

    public int size() { return positions.size(); }
}
