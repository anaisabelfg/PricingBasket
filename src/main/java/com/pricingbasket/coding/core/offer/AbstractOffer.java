package com.pricingbasket.coding.core.offer;

import com.pricingbasket.coding.core.model.Discount;
import com.pricingbasket.coding.core.model.Item;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.security.InvalidParameterException;
import java.util.List;

public abstract class AbstractOffer {
    private List<String> products;

    @JsonCreator
    public AbstractOffer(List<String> products) {

        if (null == products || products.isEmpty())
            throw new InvalidParameterException("Offer must be present");
        this.products = products;
    }

    public List<String> getProducts() {
        return products;
    }

    public abstract Boolean canBeApplied(List<Item> items);

    public abstract List<Discount> apply(List<Item> items);

}
