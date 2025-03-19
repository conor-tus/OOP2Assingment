package org.assignment;

import java.util.List;

public final class DiscountElectronics extends DiscountManager {

    @Override
    public void discountProduct(List<Product> itemCatalouge, double discountPercentage) {
        itemCatalouge.stream()
                .filter(item -> item instanceof Electronics)
                .forEach(item -> item.applyDiscount(discountPercentage));
    }

}
