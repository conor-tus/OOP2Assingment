package org.assignment;


import java.util.ArrayList;
import java.util.List;

abstract public class Product implements Discountable {

    enum ProductCategory {
        ELECTRONICS,
        CLOTHING,
        GROCERIES
    }

    private static int idCounter = 0;
    private static List<Product> itemCatalouge = new ArrayList<>();
    private final String id;
    private final String name;
    private double price;


    public Product( String name, double price) {
        this.id = String.valueOf(++idCounter);
        this.name = name;
        this.price = price;
        itemCatalouge.add(this);
    }


    public void getDetails(){
    }

    @Override
    public void applyDiscount(double discount) {
        this.price = (this.price/100)*(100-discount);
    }

    public String getId() {return id;}

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

}

class Electronics extends Product{

    public Electronics(String name, double price) {
        super(name, price);
        ProductCategory category = ProductCategory.ELECTRONICS;
    }

    public void getElectronicWarranty(){
        System.out.println("Electronic Warranty is called");
    }

    @Override
    public void getDetails(){
        System.out.println("Product is called "+ this.getName());
        System.out.println("Product is priced "+ this.getPrice());
        System.out.println("Product is of Product Category "+ ProductCategory.ELECTRONICS);
    }
}

class Clothing extends Product{

    public Clothing( String name, double price) {
        super(name, price);
        ProductCategory category = ProductCategory.CLOTHING;
    }

    public void getClothingReturnPolicy(){
        System.out.println("Clothing Return Policy is called");
    }

    public void getClothingReturnPolicy(boolean isItemFromASale ){
        if (isItemFromASale) {
            System.out.println("Clothing return policy not applicable");
        }
        else{
            System.out.println("Clothing Return Policy is called");
        }

    }

    @Override
    public void getDetails(){
        System.out.println("Product is called "+ this.getName());
        System.out.println("Product is priced "+ this.getPrice());
        System.out.println("Product is of Product Category "+ ProductCategory.CLOTHING);
    }
}
