package com.zyl.arithmetrc.interview.jingbeifang;

import java.math.BigDecimal;

public class FullDiscountPrice implements Price{
    private static final double FULL_DISCOUNT_THRESHOLD = 100;
    private static final double FULL_DISCOUNT_AMOUNT = 10;

    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }

    @Override
    public double sellPrice(double price) {
        return price >= FULL_DISCOUNT_THRESHOLD ?
                BigDecimal.valueOf(price).subtract(BigDecimal.valueOf(FULL_DISCOUNT_AMOUNT)).doubleValue() : price;
    }
}
