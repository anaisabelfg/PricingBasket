package com.pricingbasket.coding;

import com.pricingbasket.coding.core.BasicCheckout;
import com.pricingbasket.coding.core.OfferMatcher;
import com.pricingbasket.coding.core.model.Item;
import com.pricingbasket.coding.core.model.Offer;
import com.pricingbasket.coding.core.offer.AbstractOffer;
import com.pricingbasket.coding.core.offer.OfferFactory;
import com.pricingbasket.coding.infrastructure.ListReader;

import java.util.List;

public class CheckoutApplication {

    public static void main(String[] args) {

        if (args.length < 1) {
            throw new RuntimeException("No path to the input list was provided.");
        }

        List<Item> itemList = new ListReader<Item>().read(args[0], Item.class);

        if (args.length > 1) {

            List<Offer> offersList = new ListReader<Offer>().read(args[1], Offer.class);
            List<AbstractOffer> offers = OfferFactory.getOffers(offersList);

            itemList = OfferMatcher.apply(itemList, offers);
        }

        new BasicCheckout().checkout(itemList);
    }
}
