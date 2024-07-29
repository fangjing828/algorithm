package org.example;

import java.math.BigDecimal;

public interface BigDecimals {
    BigDecimal E0 = BigDecimal.ONE;
    BigDecimal E1 = BigDecimal.TEN;
    BigDecimal E2 = new BigDecimal("100");
    BigDecimal E3 = new BigDecimal("1000");
    BigDecimal E4 = new BigDecimal("10000");
    BigDecimal E5 = new BigDecimal("100000");
    BigDecimal E6 = new BigDecimal("1000000");
    BigDecimal E7 = new BigDecimal("10000000");
    BigDecimal E8 = new BigDecimal("100000000");
    BigDecimal E9 = new BigDecimal("1000000000");
    BigDecimal E10 = new BigDecimal("10000000000");
    BigDecimal E11 = new BigDecimal("100000000000");
    BigDecimal E12 = new BigDecimal("1000000000000");
    BigDecimal E13 = new BigDecimal("10000000000000");
    BigDecimal E14 = new BigDecimal("100000000000000");
    BigDecimal E15 = new BigDecimal("1000000000000000");
    BigDecimal E16 = new BigDecimal("10000000000000000");
    BigDecimal E17 = new BigDecimal("100000000000000000");
    BigDecimal E18 = new BigDecimal("1000000000000000000");
    BigDecimal E19 = new BigDecimal("10000000000000000000");
    BigDecimal E20 = new BigDecimal("100000000000000000000");

    static BigDecimal getTenPowerValue(int scale) {
        switch (scale) {
            case 0:
                return E0;
            case 1:
                return E1;
            case 2:
                return E2;
            case 3:
                return E3;
            case 4:
                return E4;
            case 5:
                return E5;
            case 6:
                return E6;
            case 7:
                return E7;
            case 8:
                return E8;
            case 9:
                return E9;
            case 10:
                return E10;
            case 11:
                return E11;
            case 12:
                return E12;
            case 13:
                return E13;
            case 14:
                return E14;
            case 15:
                return E15;
            case 16:
                return E16;
            case 17:
                return E17;
            case 18:
                return E18;
            case 19:
                return E19;
            case 20:
                return E20;
            default:
                return new BigDecimal(String.valueOf(Math.pow(10, scale)));
        }
    }

    static BigDecimal convertE8(String data) {
        if (data == null || data.isBlank()) {
            return BigDecimal.ZERO;
        } else {
            return new BigDecimal(data).divide(E8);
        }
    }
}
