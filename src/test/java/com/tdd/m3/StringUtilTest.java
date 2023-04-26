package com.tdd.m3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class StringUtilTest {

    @ParameterizedTest
    @MethodSource("validLimitProvider")
    public void limitReached_stringTruncates(int limit, String output) {
        String input = "The economy is about to";
        Assertions.assertEquals(output, StringUtil.truncateWithEllipsis(input, limit));
    }

    public static Stream<Arguments> validLimitProvider() {
        return Stream.of(
                Arguments.of(1, "T..."),
                Arguments.of(11, "The economy...")
        );
    }

    @ParameterizedTest
    @MethodSource("inputOutputLimitProvider")
    public void limitNotReached_StringNotChanged(String input, int limit) {
        Assertions.assertEquals("The economy is about to", StringUtil.truncateWithEllipsis(input, limit));
    }

    public static Stream<Arguments> inputOutputLimitProvider() {
        String input = "The economy is about to";
        return Stream.of(
        Arguments.of(input, 40),
        Arguments.of(input, input.length()) // at border, input length == limit
        );
    }

    @ParameterizedTest
    @MethodSource("invalidArgumentProvider")
    public void invalidInput_isRejected() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> StringUtil.truncateWithEllipsis(null, 5));
    }

    public static Stream<Arguments> invalidArgumentProvider() {
        return Stream.of(
                Arguments.of(null, 5),
                Arguments.of("Some input", 0)
        );
    }

    @ParameterizedTest
    @MethodSource("shortInputLessOrEqualToEllipsis")
    public void inputShorterOrEqualThanLimit_StringNotChanged(String input, int limit) {
        Assertions.assertEquals(input, StringUtil.truncateWithEllipsis(input, limit));
    }

    public static Stream<Arguments> shortInputLessOrEqualToEllipsis() {
        return Stream.of(
                Arguments.of("The", 2),
                Arguments.of("The", 3)
        );
    }
}
