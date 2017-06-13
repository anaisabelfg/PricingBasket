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

public class FixedPercentage50OfferTest {

    private List<String> product = new ArrayList<String>() {{
        add("Cereal");
    }};
    private FixedPercentage50Offer fixedPercentage50OfferOneProduct
            = new FixedPercentage50Offer(product);
    private List<String> products = new ArrayList<String>() {{
        add("Cereal");
        add("Wine");
    }};
    private FixedPercentage50Offer fixedPercentage50OfferMultipleProducts
            = new FixedPercentage50Offer(products);

    @Test
    public void shouldReturnNoDiscountsWhenItemsAreNotPresent() {

        assertThat(fixedPercentage50OfferOneProduct.apply(new ArrayList<>()), is(new ArrayList<Discount>()));

        assertThat(fixedPercentage50OfferOneProduct.apply(null), is(new ArrayList<Discount>()));

    }

    @Test
    public void shouldReturnInvalidParameterExceptionWhenItemsPatternAreNotPresent() {

        assertThatThrownBy(() -> new FixedPercentage50Offer(null))
                .isInstanceOf(InvalidParameterException.class);


        assertThatThrownBy(() -> new FixedPercentage50Offer(new ArrayList<>()))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    public void shouldReturnNoDiscountsWhenNoMatchesFound() {

        List<Item> items = new ArrayList<Item>() {{
            add(new Item("Cheese", new BigDecimal(3.25)));
        }};

        assertThat(fixedPercentage50OfferOneProduct.apply(items), is(new ArrayList<Discount>()));

        assertThat(fixedPercentage50OfferMultipleProducts.apply(items), is(new ArrayList<Discount>()));

    }

    @Test
    public void shouldReturnDiscountsListWhenMatchesFound() {

        List<Item> items = new ArrayList<Item>() {{
            add(new Item("Cereal", new BigDecimal(1.5)));
        }};

        List<Discount> expectedItems = new ArrayList<Discount>() {{
            add(new Discount(items, new Item("Discount 50% Cereal", new BigDecimal(-0.75))));
        }};

        assertThat(fixedPercentage50OfferOneProduct.apply(items), is(expectedItems));

        assertThat(fixedPercentage50OfferMultipleProducts.apply(items), is(expectedItems));
    }
}