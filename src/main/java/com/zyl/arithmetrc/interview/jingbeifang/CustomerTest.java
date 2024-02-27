package com.zyl.arithmetrc.interview.jingbeifang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomerTest {

    public static void main(String[] args) {
        // costomer A
        Fruit apple = new Fruit("apple", 8, null);
        Fruit strawberry = new Fruit("strawberry", 13, null);

        ShoppingList shoppingListA  = new ShoppingList();
        shoppingListA.add(apple, 5);
        shoppingListA.add(strawberry, 7);
        System.out.println("costomer A need :" +  (40 + 13 * 7 )
                + " ; calc : " + shoppingListA.getTotalPrice());

        // costomer B
        ShoppingList shoppingListB  = new ShoppingList();

        Fruit mongo = new Fruit("mongo", 20, null);
        shoppingListB.add(apple, 5);
        shoppingListB.add(strawberry, 7);
        shoppingListB.add(mongo, 3);
        System.out.println("costomer B need :" +  (40 + 13 * 7 + 20 * 3 )
                + " ; calc : " + shoppingListB.getTotalPrice());

        // costomer C
        ShoppingList shoppingListC  = new ShoppingList();
        List<Price> strawberryPriceList = new ArrayList<>();
        strawberryPriceList.add(new StrawberryDiscountPrice());
        Fruit strawberryDisPrice = new Fruit("strawberry", 13, strawberryPriceList);
        shoppingListC.add(apple, 5);
        shoppingListC.add(strawberryDisPrice, 7);
        shoppingListC.add(mongo, 3);
        System.out.println("costomer C need :" +  (40 + 13 * 7 * 0.8 + 20 * 3)
                + " ; calc : " + shoppingListC.getTotalPrice());

        // costomer D
        ShoppingList shoppingListD  = new ShoppingList();
        shoppingListD.add(apple, 5);
        shoppingListD.add(strawberryDisPrice, 7);
        shoppingListD.add(mongo, 3);
        shoppingListD.addFullPrice(Collections.singletonList(new FullDiscountPrice()));
        System.out.println("costomer D need :" +  (40 + 13 * 7 * 0.8 + 20 * 3 - 10)
                + " ; calc : " + shoppingListD.getTotalPrice());

    }
}
