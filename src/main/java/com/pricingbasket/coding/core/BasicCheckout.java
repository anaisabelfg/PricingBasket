package com.pricingbasket.coding.core;

import com.pricingbasket.coding.core.model.Item;

import java.math.BigDecimal;
import java.util.List;

public class BasicCheckout implements Checkout {

    @Override
    public String checkout(List<Item> items) {

        if (null == items || items.isEmpty()) return "No items";

        StringBuilder stringBuilder = new StringBuilder();

        items.forEach(item -> stringBuilder
                .append(item.getName())
                .append(" : £")
                .append(String.format("%.2f", item.getPrice()))
                .append("\n"));

        BigDecimal sum = items.stream().map(Item::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        stringBuilder.append("total price : £").append(String.format("%.2f", sum)).append("\n");

        String output = stringBuilder.toString();

        System.out.print(output);

        return output;
    }

}
