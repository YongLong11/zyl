package com.zyl.arithmetrc.interview.jingbeifang;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Fruit {
    private String name;
    private double price;
    private List<Price> DiscountPrices;
}
