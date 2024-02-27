package com.zyl.arithmetrc.interview.jingbeifang;

public class NoDiscountPrice implements Price{

    @Override
    public int order() {
        return Integer.MIN_VALUE;
    }

    @Override
    public double sellPrice(double price) {
        return price;
    }
}
