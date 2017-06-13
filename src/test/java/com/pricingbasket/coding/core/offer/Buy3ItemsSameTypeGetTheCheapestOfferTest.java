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

public class Buy3ItemsSameTypeGetTheCheapestOfferTest {

    private List<String> product = new ArrayList<String>() {{
        add("Cereal");
    }};
    private Buy3ItemsSameTypeGetTheCheapestOffer buy3ItemsSameTypeGetTheCheapestOfferOneProduct
            = new Buy3ItemsSameTypeGetTheCheapestOffer(product);

    @Test
    public void shouldReturnNoDiscountsWhenItemsAreNotPresent() {

        assertThat(buy3ItemsSameTypeGetTheCheapestOfferOneProduct.apply(new ArrayList<>()), is(new ArrayList<Discount>()));

        assertThat(buy3ItemsSameTypeGetTheCheapestOfferOneProduct.apply(null), is(new ArrayList<Discount>()));
    }

    @Test
    public void shouldReturnInvalidParameterExceptionWhenItemsPatternAreNotPresent() {

        assertThatThrownBy(() -> new Buy3ItemsSameTypeGetTheCheapestOffer(null))
                .isInstanceOf(InvalidParameterException.class);


        assertThatThrownBy(() -> new Buy3ItemsSameTypeGetTheCheapestOffer(new ArrayList<>()))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    public void shouldReturnNoDiscountsWhenNoMatchesFound() {

        List<Item> items = new ArrayList<Item>() {{
            add(new Item("Cheese", new BigDecimal(3.25)));
            add(new Item("Cheese", new BigDecimal(3.25)));
            add(new Item("Cereal", new BigDecimal(1.5)));
        }};

        assertThat(buy3ItemsSameTypeGetTheCheapestOfferOneProduct.apply(items), is(new ArrayList<Discount>()));

    }

    @Test
    public void shouldReturnDiscountsListWhenMatchesFound() {

        List<Item> itemsOfferCereal = new ArrayList<Item>() {{
            add(new Item("Cereal", new BigDecimal(1.75)));
            add(new Item("Cereal", new BigDecimal(1.5)));
            add(new Item("Cereal", new BigDecimal(1.25)));
        }};

        List<Discount> expectedItems = new ArrayList<Discount>() {{
            add(new Discount(itemsOfferCereal, new Item("3 for 2 Cereal", new BigDecimal(-1.25))));
        }};

        assertThat(buy3ItemsSameTypeGetTheCheapestOfferOneProduct.apply(itemsOfferCereal), is(expectedItems));

    }


}