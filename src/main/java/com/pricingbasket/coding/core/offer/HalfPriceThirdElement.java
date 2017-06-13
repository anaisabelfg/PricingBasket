package com.pricingbasket.coding.core.offer;

import com.pricingbasket.coding.core.model.Discount;
import com.pricingbasket.coding.core.model.Item;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HalfPriceThirdElement extends AbstractOffer {

    public HalfPriceThirdElement(List<String> products) {
        super(products);

        if (products.size() != 3)
            throw new InvalidParameterException("Products size must be three for this offer");

    }

    @Override
    public Boolean canBeApplied(List<Item> items) {

        Boolean product1Match = items.stream()
                .filter(item -> item.getName().equals(getProducts().get(0)))
                .count() > 2;

        Boolean product2Match = items.stream()
                .filter(item -> item.getName().equals(getProducts().get(2)))
                .count() > 1;

        return product1Match && product2Match;
    }

    @Override
    public List<Discount> apply(List<Item> items) {
        if (null == items || items.isEmpty())
            return new ArrayList<>();

        List<Discount> discounts = new ArrayList<>();
        discounts.addAll(createItemsDiscount(items));
        return discounts;
    }

    private List<Discount> createItemsDiscount(List<Item> items) {
        List<Discount> discounts = new ArrayList<>();

        List<Item> itemsByProduct1 = items.stream()
                .filter(item -> item.getName().contains(getProducts().get(0)))
                .collect(Collectors.toList());

        int product1Matches = itemsByProduct1.size() / 2;

        List<Item> itemsByProduct2 = items.stream()
                .filter(item -> item.getName().contains(getProducts().get(2)))
                .collect(Collectors.toList());

        int product2Matches = itemsByProduct2.size();

        int numDiscounts = product1Matches - (product1Matches - product2Matches);

        int j = 0;
        for (int i = 0; i < numDiscounts && product1Matches > 0 && j <= numDiscounts - 1; i++) {
            discounts.add(createDiscount(itemsByProduct1.get(j), itemsByProduct1.get(j + 1), itemsByProduct2.get(i)));
            j = j + 2;
        }

        return discounts;
    }

    private Discount createDiscount(Item item1, Item item2, Item item3) {
        List<Item> itemsInvolvedOffer = new ArrayList<>();
        itemsInvolvedOffer.add(item1);
        itemsInvolvedOffer.add(item2);
        itemsInvolvedOffer.add(item3);

        Item discountItem = new Item("Buy 2 " + item1.getName() + " and get 50% in " + item3.getName(),
                item3.getPrice().divide(new BigDecimal(2)).negate());

        return new Discount(itemsInvolvedOffer, discountItem);
    }

}
