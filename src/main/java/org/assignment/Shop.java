package org.assignment;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class Shop {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

    private List<Product> basket;
    private static final Path FILE_DIRECTORY = Paths.get("receipts");
    static final List<String> FILE_NAMES = List.of("CustomerReceipt.txt","ShopReceipt.txt");

    public Shop(){
        this.basket = new ArrayList<>();
    }

    public void addProductToBasket(Product p){
        this.basket.add(p);
    }

    public void addMultipleProductsToBasket(Product... product){
        this.basket.addAll(Arrays.asList(product));
        this.basket.sort(Comparator.comparing(Product::getName));
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

    public static void createReceiptFiles(ResourceBundle messages) throws IOException {
        for (String filename : FILE_NAMES) {
            Path filePath = FILE_DIRECTORY.resolve(filename);
            if (!Files.exists(filePath)) {
                String content = messages.getString("fileContent") + filename; // Sample content
                Files.write(filePath, content.getBytes(StandardCharsets.UTF_8));
                System.out.println(messages.getString("fileCreated") + filename);
            }
        }
    }

    public static void processFile(String filename,String fileContent, ResourceBundle messages) throws IOException {
        Path filePath = FILE_DIRECTORY.resolve(filename);

        // Read file content
        String content = Files.readString(filePath, StandardCharsets.UTF_8);
        System.out.println(messages.getString("processingFile") + filename);

        // Simulate processing: Uppercase the content
        String processedContent = content.toUpperCase();

        // Write the processed content to another file
        Path processedFile = FILE_DIRECTORY.resolve("processed_" + filename);
        Files.write(processedFile, processedContent.getBytes(StandardCharsets.UTF_8));
        String updatedContent = "";
        if (filename.contains("Customer")) {
            updatedContent = "This is the Customers receipt" + fileContent;
        }
        else if (filename.contains("Shop")) {
            updatedContent = "This is the Shops receipt" + fileContent;
        }
        Files.write(processedFile, updatedContent.getBytes(StandardCharsets.UTF_8));
        System.out.println(messages.getString("fileProcessed") + processedFile.getFileName());
    }

    public static void main(String[] args) {

        Locale local = Locale.getDefault();

        
        //Locale locale = new Locale("ga", "IE");
        ResourceBundle messages = ResourceBundle.getBundle("Messages", local);

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

        Receipt receipt = shop.buyBasket(100);


        System.out.println(shop);
        System.out.println(receipt.formattedReceipt());

        // Create the directory if it doesn't exist
        try {
            Files.createDirectories(Paths.get("receipts"));
        } catch (IOException e) {
            System.err.println(messages.getString("error.creatingDirectory"));
            return;
        }

        // Create test files
        try {
            createReceiptFiles(messages);
        } catch (IOException e) {
            System.err.println(messages.getString("error.creatingFiles"));
            return;
        }

        // Start processing the files concurrently using Callable tasks
        List<Callable<Void>> tasks = FILE_NAMES.stream()
                .map(filename -> (Callable<Void>) () -> {
                    try {
                        processFile(filename, receipt.formattedReceipt(),messages);
                    } catch (IOException e) {
                        System.err.println(messages.getString("error.processingFile") + filename);
                    }
                    return null;
                })
                .collect(Collectors.toList());

        try {
            // Submit tasks to ExecutorService and wait for completion
            List<Future<Void>> futures = executorService.invokeAll(tasks);

            // Wait for all tasks to complete and handle results
            for (Future<Void> future : futures) {
                try {
                    future.get();
                } catch (ExecutionException e) {
                    System.err.println(messages.getString("error.execution"));
                }
            }

            System.out.println(messages.getString("allTasksCompleted"));
        } catch (InterruptedException e) {
            System.err.println(messages.getString("error.executorInterrupted"));
        } finally {
            executorService.shutdown();
        }
    }

}