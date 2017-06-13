package com.pricingbasket.coding.core.offer;

import com.pricingbasket.coding.core.model.Discount;
import com.pricingbasket.coding.core.model.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Buy2IdenticalItemsFor2PoundsOffer extends AbstractOffer {

    public Buy2IdenticalItemsFor2PoundsOffer(List<String> products) {
        super(products);
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

        Map<String, List<Item>> itemsInvolvedPattern = getItemsInvolved(items);

        return itemsInvolvedPattern.entrySet().stream()
                .flatMap(this::toDiscountList)
                .collect(Collectors.toList());


    }

    private Stream<? extends Discount> toDiscountList(Map.Entry<String, List<Item>> iPattern) {
        List<Discount> discounts = new ArrayList<>();
        List<Item> values = iPattern.getValue();

        for (int i = 0; i < values.size() / 2 * 2; i = i + 2) {
            discounts.add(createDiscount(values.get(i), values.get(i + 1)));
        }

        return discounts.stream();
    }

    private Map<String, List<Item>> getItemsInvolved(List<Item> items) {
        return items.stream()
                .filter(item -> item.getName().contains(getProducts().get(0)))
                .sorted(Comparator.comparing(Item::getPrice).reversed())
                .collect(Collectors.groupingBy(item -> item.getName() + "-" + item.getPrice()));
    }

    private Discount createDiscount(Item item1, Item item2) {
        List<Item> itemsInvolvedOffer = new ArrayList<>();
        itemsInvolvedOffer.add(item1);
        itemsInvolvedOffer.add(item2);

        Item discountItem = new Item("2 for 2£ " + item1.getName(),
                item1.getPrice().add(item2.getPrice()).subtract(new BigDecimal(2)).negate());

        return new Discount(itemsInvolvedOffer, discountItem);
    }

    @Override
    public Boolean canBeApplied(List<Item> items) {

        return items.stream()
                .filter(item -> item.getName().contains(getProducts().get(0)))
                .collect(Collectors.groupingBy(item -> item.getName() + "-" + item.getPrice()))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .count() > 0;

    }

}
