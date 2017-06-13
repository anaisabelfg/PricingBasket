package com.pricingbasket.coding.core.offer;

import com.pricingbasket.coding.core.model.Offer;

import java.util.List;
import java.util.stream.Collectors;

public class OfferFactory {

    public static AbstractOffer getOffer(Offer offer) {

        if (offer == null) return null;

        switch (offer.getName()) {
            case "Discount 50%":
                return new FixedPercentage50Offer(offer.getProducts());
            case "2 for 2£":
                return new Buy2IdenticalItemsFor2PoundsOffer(offer.getProducts());
            case "3 for 2":
                return new Buy3ItemsSameTypeGetTheCheapestOffer(offer.getProducts());
            case "Half Price Third Product":
                return new HalfPriceThirdElement(offer.getProducts());
            default:
                return null;

        }
    }

    public static List<AbstractOffer> getOffers(List<Offer> offers) {
        return offers.stream().map(OfferFactory::getOffer).collect(Collectors.toList());
    }
}
