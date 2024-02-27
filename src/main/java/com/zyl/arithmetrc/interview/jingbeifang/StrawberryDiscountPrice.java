package com.zyl.arithmetrc.interview.jingbeifang;

import java.math.BigDecimal;

public class StrawberryDiscountPrice implements Price{
    private static final double DISCOUNT_PERCENTAGE = 0.2;

    @Override
    public double sellPrice(double price) {
        return BigDecimal.valueOf(price)
                .multiply(BigDecimal.valueOf(1 - DISCOUNT_PERCENTAGE))
                .doubleValue();
    }

    @Override
    public int order() {
        return 1;
    }
}
