package com.pricingbasket.coding.core.offer;

import com.pricingbasket.coding.core.model.Discount;
import com.pricingbasket.coding.core.model.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FixedPercentage50Offer extends AbstractOffer {

    public FixedPercentage50Offer(List<String> products) {
        super(products);
    }

    @Override
    public List<Discount> apply(List<Item> items) {

        if (null == items || items.isEmpty())
            return new ArrayList<>();

        return items.stream()
                .filter(item -> getProducts().contains(item.getName()))
                .map(toDiscount()).collect(Collectors.toList());

    }

    private Function<Item, Discount> toDiscount() {
        return item -> {
            List<Item> itemsInvolved = new ArrayList<>();
            itemsInvolved.add(item);
            Item discountItem = new Item("Discount 50% " + item.getName(),
                    item.getPrice().multiply(new BigDecimal(0.5)).negate());

            return new Discount(itemsInvolved, discountItem);
        };
    }

    @Override
    public Boolean canBeApplied(List<Item> items) {

        return items.stream()
                .filter(item -> item.getName().equals(getProducts().get(0)))
                .count() > 1;

    }

}
