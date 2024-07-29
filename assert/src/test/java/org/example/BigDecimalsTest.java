package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BigDecimalsTest {
    @Test
    void testConvertE8() {
        assertEquals("0.00000123", BigDecimals.convertE8("123").toPlainString());
        assertEquals("0.12345678", BigDecimals.convertE8("12345678").toPlainString());
        assertEquals("9.12345678", BigDecimals.convertE8("912345678").toPlainString());
    }
}