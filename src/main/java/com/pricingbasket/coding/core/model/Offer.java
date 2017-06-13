package com.pricingbasket.coding.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.security.InvalidParameterException;
import java.util.List;

public class Offer {

    private String name;
    private List<String> products;

    @JsonCreator
    public Offer(@JsonProperty("name") String name,
                 @JsonProperty("products") List<String> products) {

        if (null == products || products.isEmpty())
            throw new InvalidParameterException("Products must be present");
        this.name = name;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public List<String> getProducts() {
        return products;
    }

}
