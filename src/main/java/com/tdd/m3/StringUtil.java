package com.tdd.m3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtil {

    //TODO: test around the new cutOffChars parameter
    public static String truncate(String input, int limit, String cutOffChars) {
        if(input == null) {
            throw new IllegalArgumentException("String input must not be null");
        }
        if(limit < 1) {
            throw new IllegalArgumentException("Limit input must be greater than 0");
        }

        String ellipsis = "...";
        if(inputTooShort(input, limit, ellipsis)) {
            return input;
        }

        return input.substring(0, limit) + ellipsis;
    }

    public static String truncateWithEllipsis(String input, int limit) {
        return truncate(input, limit, "...");
    }

    private static boolean inputTooShort(String input, int limit, String ellipsis) {
        return input.length() <= ellipsis.length() || input.length() <= limit;
    }


}