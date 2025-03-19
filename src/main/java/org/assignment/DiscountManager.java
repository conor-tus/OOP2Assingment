package org.assignment;

import java.util.List;

public sealed abstract class DiscountManager permits DiscountElectronics, DiscountClothing {

    public abstract void discountProduct(List<Product> itemCatalouge, double discountPercentage);
}

