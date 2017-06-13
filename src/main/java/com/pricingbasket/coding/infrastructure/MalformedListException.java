package com.pricingbasket.coding.infrastructure;

public class MalformedListException extends RuntimeException {
    public MalformedListException(Exception e) {
        super(e);
    }
}
