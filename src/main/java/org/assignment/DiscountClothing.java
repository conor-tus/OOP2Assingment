package org.assignment;

import java.util.List;

public final class DiscountClothing extends DiscountManager {
    
    @Override
    public void discountProduct(List<Product> itemCatalouge, double discountPercentage) {
        itemCatalouge.stream()
                .filter(item -> item instanceof Clothing)
                .forEach(item -> item.applyDiscount(discountPercentage));
    }

}
