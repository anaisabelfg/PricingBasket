package com.pricingbasket.coding.core;

import com.pricingbasket.coding.core.model.Item;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BasicCheckoutTest {

    private BasicCheckout basicCheckout = new BasicCheckout();

    @Test
    public void shouldReturnEmptyOutputWhenItemsAreNotPresent() {

        assertThat(basicCheckout.checkout(new ArrayList<>()), is("No items"));

        assertThat(basicCheckout.checkout(null), is("No items"));

    }

    @Test
    public void shouldPrintListWithDiscountWhenItemsHaveDiscount() {

        List<Item> items = new ArrayList<Item>() {{
            add(new Item("Cereal", new BigDecimal(1.5)));
            add(new Item("Cereal", new BigDecimal(1.5)));
            add(new Item("Milk", new BigDecimal(1.75)));
            add(new Item("Milk", new BigDecimal(1.5)));
            add(new Item("Milk", new BigDecimal(1.25)));
            add(new Item("2 for 2£ Cereal", new BigDecimal(-1.0)));
            add(new Item("3 for 2 Milk", new BigDecimal(-1.25)));

        }};

        String output = "Cereal : £1.50\n"
                + "Cereal : £1.50\n"
                + "Milk : £1.75\n"
                + "Milk : £1.50\n"
                + "Milk : £1.25\n"
                + "2 for 2£ Cereal : £-1.00\n"
                + "3 for 2 Milk : £-1.25\n"
                + "total price : £5.25\n";


        assertThat(basicCheckout.checkout(items), is(output));

    }

    @Test
    public void shouldPrintListWhenNoDiscount() {

        List<Item> items = new ArrayList<Item>() {{
            add(new Item("Cereal", new BigDecimal(1.5)));
            add(new Item("Milk", new BigDecimal(1.75)));
        }};

        String output = "Cereal : £1.50\n"
                + "Milk : £1.75\n"
                + "total price : £3.25\n";

        assertThat(basicCheckout.checkout(items), is(output));

    }
}