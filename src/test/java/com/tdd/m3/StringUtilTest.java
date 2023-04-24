package com.tdd.m3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilTest {

    @Test
    public void limitReached_StringTruncates() {

        String input = "The economy is about to";  // length 23
        int limit = 11;

        Assertions.assertEquals("The economy...", StringUtil.truncate(input, limit));
    }
}
