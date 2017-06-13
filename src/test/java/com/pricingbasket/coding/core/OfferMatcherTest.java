package com.pricingbasket.coding.core;

import com.pricingbasket.coding.core.model.Discount;
import com.pricingbasket.coding.core.model.Item;
import com.pricingbasket.coding.core.offer.AbstractOffer;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class OfferMatcherTest {

    private Item cereal = new Item("Cereal", new BigDecimal(1.5));
    private Item milk = new Item("Milk", new BigDecimal(1.8));
    private List<Item> oneItemList = new ArrayList<Item>() {{
        add(cereal);
    }};
    private Item halfPriceCereal = new Item("Discount 50% Cereal", new BigDecimal(-0.75));
    private AbstractOffer fixedDiscountCerealX1Mock = new AbstractOffer(
            new ArrayList<String>() {{
                add("Cereal");
            }}) {

        @Override
        public Boolean canBeApplied(List<Item> items) {
            return true;
        }

        @Override
        public List<Discount> apply(List<Item> items) {
            return new ArrayList<Discount>() {{
                add(new Discount(oneItemList, halfPriceCereal));
            }};
        }
    };
    private List<AbstractOffer> oneOfferList = new ArrayList<AbstractOffer>() {{
        add(fixedDiscountCerealX1Mock);
    }};
    private List<Item> expectedItems = new ArrayList<Item>() {{
        add(cereal);
        add(cereal);
        add(cereal);
        add(milk);
        add(halfPriceCereal);
        add(halfPriceCereal);
        add(halfPriceCereal);
    }};
    private List<Item> itemList = new ArrayList<Item>() {{
        add(cereal);
        add(cereal);
        add(cereal);
        add(milk);
    }};
    private List<Item> itemsInvolved = new ArrayList<Item>() {{
        add(cereal);
        add(cereal);
        add(milk);
    }};

    @Test
    public void shouldReturnTheItemListWhenItemOrOffersAreNotPresent() {

        List<Item> emptyItemList = new ArrayList<>();
        List<AbstractOffer> emptyAbstractOfferList = new ArrayList<>();

        assertThat(OfferMatcher.apply(oneItemList, null), is(oneItemList));
        assertNull(OfferMatcher.apply(null, oneOfferList));

        assertNull(OfferMatcher.apply(null, emptyAbstractOfferList));
        assertThat(OfferMatcher.apply(emptyItemList, null), is(emptyItemList));

        assertThat(OfferMatcher.apply(oneItemList, emptyAbstractOfferList), is(oneItemList));
        assertThat(OfferMatcher.apply(emptyItemList, oneOfferList), is(emptyItemList));

        assertNull(OfferMatcher.apply(null, null));
        assertThat(OfferMatcher.apply(emptyItemList, emptyAbstractOfferList), is(emptyItemList));

    }

    @Test
    public void shouldApplyOneOffer() throws Exception {

        List<Item> itemList = new ArrayList<Item>() {{
            add(cereal);
            add(milk);
            add(milk);
        }};

        List<AbstractOffer> abstractOfferList = new ArrayList<AbstractOffer>() {{
            add(fixedDiscountCerealX1Mock);
        }};

        List<Item> expectedItems = new ArrayList<Item>() {{
            addAll(itemList);
            add(new Item("Discount 50% Cereal", new BigDecimal(-0.75)));
        }};

        assertThat(OfferMatcher.apply(itemList, abstractOfferList), is(expectedItems));
    }

    @Test
    public void shouldApplySeveralOffers() throws Exception {

        List<Item> itemList = new ArrayList<Item>() {{
            add(cereal);
            add(milk);
            add(milk);
        }};

        AbstractOffer twoForOneMilkMock = new AbstractOffer(
                new ArrayList<String>() {{
                    add("Milk");
                }}) {

            @Override
            public Boolean canBeApplied(List<Item> items) {
                return true;
            }

            @Override
            public List<Discount> apply(List<Item> items) {
                return new ArrayList<Discount>() {{
                    List<Item> milkMatchItemList = new ArrayList<Item>() {{
                        add(milk);
                        add(milk);
                    }};

                    Item milkDiscount = new Item("2 for 1 in milk", new BigDecimal(-1.8));

                    add(new Discount(milkMatchItemList, milkDiscount));
                }};
            }
        };

        List<AbstractOffer> abstractOfferList = new ArrayList<AbstractOffer>() {{
            add(fixedDiscountCerealX1Mock);
            add(twoForOneMilkMock);
        }};
        List<Item> expectedItems = new ArrayList<Item>() {{
            add(cereal);
            add(milk);
            add(milk);
            add(new Item("Discount 50% Cereal", new BigDecimal(-0.75)));
            add(new Item("2 for 1 in milk", new BigDecimal(-1.8)));
        }};

        List<Item> itemsWithAppliedOffers = OfferMatcher.apply(itemList, abstractOfferList);

        assertThat(itemsWithAppliedOffers, is(expectedItems));
    }

    @Test
    public void shouldApplyFirstOfferMatch() throws Exception {

        AbstractOffer fixedDiscountCerealX3Mock = new AbstractOffer(
                new ArrayList<String>() {{
                    add("Cereal");
                }}) {

            @Override
            public Boolean canBeApplied(List<Item> items) {
                return true;
            }

            @Override
            public List<Discount> apply(List<Item> items) {
                return new ArrayList<Discount>() {{
                    add(new Discount(oneItemList, halfPriceCereal));
                    add(new Discount(oneItemList, halfPriceCereal));
                    add(new Discount(oneItemList, halfPriceCereal));
                }};
            }
        };

        AbstractOffer threeForTwoCerealMock = new AbstractOffer(
                new ArrayList<String>() {{
                    add("Cereal");
                }}) {

            @Override
            public Boolean canBeApplied(List<Item> items) {
                return true;
            }

            @Override
            public List<Discount> apply(List<Item> items) {
                return new ArrayList<Discount>() {{
                }};
            }
        };

        AbstractOffer buyTwoCerealAndGetHalfPriceMilk = new AbstractOffer(
                new ArrayList<String>() {{
                    add("Cereal");
                    add("Cereal");
                    add("Milk");
                }}) {

            @Override
            public Boolean canBeApplied(List<Item> items) {
                return true;
            }

            @Override
            public List<Discount> apply(List<Item> items) {
                return new ArrayList<Discount>() {{
                }};
            }
        };


        List<AbstractOffer> offerList = new ArrayList<AbstractOffer>() {{
            add(fixedDiscountCerealX3Mock);
            add(threeForTwoCerealMock);
            add(buyTwoCerealAndGetHalfPriceMilk);
        }};

        assertThat(OfferMatcher.apply(itemList, offerList), is(expectedItems));
    }

    @Test
    public void shouldApplyOffersUntilItemsListDontHaveAny() throws Exception {
        itemList = new ArrayList<Item>() {{
            add(cereal);
            add(cereal);
            add(cereal);
            add(cereal);
            add(cereal);
            add(cereal);
            add(milk);
        }};

        Item halfPriceMilk = new Item("Buy 2 cereal and get 50% in milk", new BigDecimal(-0.9));
        AbstractOffer buyTwoCerealAndGetHalfPriceMilk = new AbstractOffer(
                new ArrayList<String>() {{
                    add("Cereal");
                    add("Cereal");
                    add("Milk");
                }}) {

            @Override
            public Boolean canBeApplied(List<Item> items) {
                return true;
            }

            @Override
            public List<Discount> apply(List<Item> items) {
                return new ArrayList<Discount>() {{
                    assertEquals(items.size(), 7);
                    add(new Discount(itemsInvolved, halfPriceMilk));
                }};
            }
        };

        Item freeOneCereal = new Item("3 for 2 cereal", new BigDecimal(-1.5));
        AbstractOffer threeForTwoCerealMock = new AbstractOffer(
                new ArrayList<String>() {{
                    add("Cereal");
                }}) {

            @Override
            public Boolean canBeApplied(List<Item> items) {
                return true;
            }

            @Override
            public List<Discount> apply(List<Item> items) {
                return new ArrayList<Discount>() {{
                    assertEquals(items.size(), 4);
                    itemsInvolved = new ArrayList<Item>() {{
                        add(cereal);
                        add(cereal);
                        add(cereal);
                    }};

                    add(new Discount(itemsInvolved, freeOneCereal));
                }};
            }
        };

        fixedDiscountCerealX1Mock = new AbstractOffer(
                new ArrayList<String>() {{
                    add("Cereal");
                }}) {

            @Override
            public Boolean canBeApplied(List<Item> items) {
                return true;
            }

            @Override
            public List<Discount> apply(List<Item> items) {
                return new ArrayList<Discount>() {{
                    assertEquals(items.size(), 1);
                    add(new Discount(oneItemList, halfPriceCereal));
                }};
            }
        };


        List<AbstractOffer> offerList = new ArrayList<AbstractOffer>() {{
            add(buyTwoCerealAndGetHalfPriceMilk);
            add(threeForTwoCerealMock);
            add(fixedDiscountCerealX1Mock);

        }};

        expectedItems = new ArrayList<Item>() {{
            add(cereal);
            add(cereal);
            add(cereal);
            add(cereal);
            add(cereal);
            add(cereal);
            add(milk);
            add(halfPriceMilk);
            add(freeOneCereal);
            add(halfPriceCereal);
        }};

        assertThat(OfferMatcher.apply(itemList, offerList), is(expectedItems));
    }
}