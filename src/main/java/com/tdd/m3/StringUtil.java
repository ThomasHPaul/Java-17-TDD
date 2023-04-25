package com.tdd.m3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtil {

    public static String truncate(String input, int limit) {
        if(input.length() <= limit) {
            return input;
        }
        return input.substring(0, limit) + "...";
    }
}