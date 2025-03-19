package org.assignment;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    private List<Product> basket;

    public Shop(){
        this.basket = new ArrayList<>();
    }

    public void addProductToBasket(Product p){
        this.basket.add(p);
    }

    public void addMultipleProductsToBasket(Product... product){
        for(Product p : product){
            this.basket.add(p);
        }
    }

    public void removeProductFromBasket(Product p){
        this.basket.remove(p);
    }

    public double getBasketTotal(){
        var total = 0.0;
        for(Product p : this.basket){
            total += p.getPrice();
        }
        return total;
    }

    public List<Product> getBasket(){
        return this.basket;
    }

    public String toString(){
        StringBuilder printedString = new StringBuilder();
        for(Product p : this.basket){
            printedString.append(p.getName()).append(" for ").append(p.getPrice()).append("\n");
        }
        double total = this.getBasketTotal();
        printedString.append("Total Products: ").append(this.basket.size()).append("\n");
        printedString.append("The total price is: ").append(String.format("%.2f",total)).append(" Euros");
        return printedString.toString();
    }

    public Receipt buyBasket(double amountPaid){
        freeGameCheck(getBasket());
        return new Receipt(1, this.getBasketTotal(),amountPaid, changeCalculator(amountPaid,this.getBasketTotal()), this.getBasket());
    }

    public double changeCalculator(double amountPaid, double totalBasketPay) throws InsufficientFundsException{
        double change = amountPaid - totalBasketPay;
        if (change < 0) {
            throw new InsufficientFundsException("Insufficient money given");
        }
        return change;
    }

    public void freeGameCheck(List<Product> basket){
        for(Product p : basket){
            switch (p.getName()){
//                case"Xbox":
//                    System.out.println("Free game! Xbox fifa 2025");
//                    break;
//                case"Switch":
//                    System.out.println("Free game! Switch fifa 2025");
//                    break;
//                case"Playstation":
//                    System.out.println("Free game! Playstation fifa 2025");
                case "Xbox" -> System.out.println("Free game! Xbox fifa 2025");
                case "Switch" -> System.out.println("Free game! Switch fifa 2025");
                case "Playstation" -> System.out.println("Free game! Playstation fifa 2025");
            }
        }
    }

    public static void main(String[] args) {

        Shop shop = new Shop();
        Electronics Nintendo = new Electronics("Switch",40.0);
        Electronics Xbox = new Electronics("Xbox 1",40.0);
        Clothing shoes =  new Clothing("Shoes",5.0);
        Clothing shirts =  new Clothing("Shirts",5.0);
        Product chocolate = new Clothing("Chocolate",3.0);

        shoes.getClothingReturnPolicy(true);
        shoes.getClothingReturnPolicy();
        Xbox.getElectronicWarranty();

        shop.addProductToBasket(Nintendo);
        shop.addProductToBasket(Xbox);
        shop.addProductToBasket(shoes);
        shop.addMultipleProductsToBasket(shirts,chocolate);

        DiscountElectronics de = new DiscountElectronics();
        de.discountProduct(shop.getBasket(),41);

        Receipt receipt = shop.buyBasket(50);


        System.out.println(shop);
        System.out.println(receipt.formattedReceipt());
    }
}