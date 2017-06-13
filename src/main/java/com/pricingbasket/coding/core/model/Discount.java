package com.pricingbasket.coding.core.model;

import java.util.List;

public class Discount {
    private List<Item> itemsInvolved;
    private Item discountItem;

    public Discount(List<Item> itemsInvolved, Item discountItem) {
        this.itemsInvolved = itemsInvolved;
        this.discountItem = discountItem;
    }

    public Item getDiscountItem() {
        return discountItem;
    }

    public List<Item> getItemsInvolved() {
        return itemsInvolved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Discount discount = (Discount) o;

        if (!itemsInvolved.equals(discount.itemsInvolved)) return false;
        return discountItem.equals(discount.discountItem);
    }

    @Override
    public int hashCode() {
        int result = itemsInvolved.hashCode();
        result = 31 * result + discountItem.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "itemsInvolved=" + itemsInvolved +
                ", discountItem=" + discountItem +
                '}';
    }
}
