package com.pricingbasket.coding.core;

import com.pricingbasket.coding.core.model.Item;

import java.util.List;

public interface Checkout {
    String checkout(List<Item> items);
}
