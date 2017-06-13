package com.pricingbasket.coding.core;

import com.pricingbasket.coding.core.model.Discount;
import com.pricingbasket.coding.core.model.Item;
import com.pricingbasket.coding.core.offer.AbstractOffer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OfferMatcher {

    public static List<Item> apply(List<Item> items, List<AbstractOffer> abstractOffers) {

        if (null == items || items.isEmpty() || null == abstractOffers || abstractOffers.isEmpty()) return items;


        List<AbstractOffer> offersCanBeApplied = offersCanBeApplied(items, abstractOffers);

        List<Discount> discountList = getDiscounts(items, offersCanBeApplied);

        return Stream.concat(items.stream(),
                discountList.stream().
                        map(Discount::getDiscountItem)
                        .collect(Collectors.toList()).stream())
                .collect(Collectors.toList());
    }

    private static List<Discount> getDiscounts(List<Item> items, List<AbstractOffer> offersCanBeApplied) {
        List<Discount> discountList = new ArrayList<>();

        List<Item> itemsCopy = new ArrayList<Item>() {{
            addAll(items);
        }};

        for (AbstractOffer offer : offersCanBeApplied) {
            List<Discount> discountsOffer = offer.apply(itemsCopy);
            removeItemsInvolvedInItemsCopy(itemsCopy, discountsOffer);
            discountList.addAll(discountsOffer);
        }

        return discountList;
    }

    private static void removeItemsInvolvedInItemsCopy(List<Item> itemsCopy, List<Discount> discountsOffer) {
        for (Discount discount : discountsOffer) {
            discount.getItemsInvolved().forEach(item -> itemsCopy.remove(item));
        }
    }

    private static List<AbstractOffer> offersCanBeApplied(List<Item> items, List<AbstractOffer> abstractOffers) {
        return abstractOffers.stream()
                    .filter(offer -> offer.canBeApplied(items))
                    .collect(Collectors.toList());
    }
}