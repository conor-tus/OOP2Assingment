package org.assignment;

import java.time.LocalDateTime;
import java.util.List;

public record Receipt(int id, double totalCost, double amountPaid, double changeGiven, List<Product> productsBought){

    public String formattedReceipt(){
        StringBuilder formattedString = new StringBuilder();
        formattedString.append("Receipt ID: ").append(id).append("\n");
        formattedString.append("Products bought: \n \n");
        for (Product product : productsBought){
            formattedString.append(product.getName()).append("\n");
        }
        formattedString.append("\n");
        formattedString.append("Total cost: ").append(String.format("%.2f", totalCost)).append("\n");
        formattedString.append("Amount paid: ").append(String.format("%.2f", amountPaid)).append("\n");
        formattedString.append("Change given: ").append(String.format("%.2f", changeGiven)).append("\n");
        formattedString.append(LocalDateTime.now());
        return formattedString.toString();
    }

}
