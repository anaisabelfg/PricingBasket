package com.pricingbasket.coding.core.offer;

import com.pricingbasket.coding.core.model.Discount;
import com.pricingbasket.coding.core.model.Item;
import org.junit.Test;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HalfPriceThirdElementTest {

    private List<String> products = new ArrayList<String>() {{
        add("Cereal");
        add("Cereal");
        add("Milk");
    }};
    private HalfPriceThirdElement halfPriceThirdElement
            = new HalfPriceThirdElement(products);

    @Test
    public void shouldReturnNoDiscountsWhenItemsAreNotPresent() {

        assertThat(halfPriceThirdElement.apply(new ArrayList<>()), is(new ArrayList<Discount>()));

        assertThat(halfPriceThirdElement.apply(null), is(new ArrayList<Discount>()));

    }

    @Test
    public void shouldReturnInvalidParameterExceptionWhenItemsPatternAreNotPresent() {

        assertThatThrownBy(() -> new HalfPriceThirdElement(null))
                .isInstanceOf(InvalidParameterException.class);


        assertThatThrownBy(() -> new HalfPriceThirdElement(new ArrayList<>()))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    public void shouldReturnNoDiscountsWhenNoMatchesFound() {

        List<Item> items = new ArrayList<Item>() {{
            add(new Item("Cheese", new BigDecimal(3.25)));
            add(new Item("Cheese", new BigDecimal(3.25)));
            add(new Item("Bread", new BigDecimal(1.5)));
        }};

        assertThat(halfPriceThirdElement.apply(items), is(new ArrayList<Discount>()));

    }

    @Test
    public void shouldReturnDiscountsListWhenMatchesFound() {

        List<Item> items = new ArrayList<Item>() {{
            add(new Item("Cereal", new BigDecimal(1.5)));
            add(new Item("Cereal", new BigDecimal(1.5)));
            add(new Item("Milk", new BigDecimal(1)));
        }};

        List<Discount> expectedItems = new ArrayList<Discount>() {{
            add(new Discount(items, new Item("Buy 2 Cereal and get 50% in Milk", new BigDecimal(-0.5))));
        }};

        assertThat(halfPriceThirdElement.apply(items), is(expectedItems));
    }


}