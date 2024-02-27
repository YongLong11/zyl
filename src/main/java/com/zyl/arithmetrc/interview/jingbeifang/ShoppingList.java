package com.zyl.arithmetrc.interview.jingbeifang;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ShoppingList {
    private  double totalPrice;
    private  List<Fruit> fruits = new ArrayList<>();

    public  void add(Fruit fruit, double weight) {
        if(fruit == null){
            throw new RuntimeException("ShoppingList is not init");
        }
        fruits.add(fruit);
        totalPrice = totalPrice + (weight * getFinalPrice(fruit));
    }
    public void addFullPrice(List<Price> fullPrice){
        if(totalPrice == 0){
            throw new RuntimeException("check total price, must cale total price before add full price");
        }
        if(fullPrice != null || !fullPrice.isEmpty()){
            for (Price price : fullPrice) {
                totalPrice = price.sellPrice(totalPrice);
            }
        }
    }

    private  double getFinalPrice(Fruit fruit){
        double price = fruit.getPrice() ;
        List<Price> discountPrices = fruit.getDiscountPrices();
        if(discountPrices == null || discountPrices.isEmpty()){
            return fruit.getPrice();
        }
        discountPrices.sort((dis1, dis2) -> dis1.order() - dis2.order());
        for (Price discountPrice : discountPrices) {
            price = discountPrice.sellPrice(price);
        }
        return price;
    }

}
