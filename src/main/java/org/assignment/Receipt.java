package org.assignment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public record Receipt(int id, double totalCost, double amountPaid, double changeGiven, List<Product> productsBought){

    //static Locale local = Locale.getDefault();
    static Locale local = new Locale("ga", "IE");
    static ResourceBundle messages = ResourceBundle.getBundle("Messages", local);

    public String formattedReceipt(){
        StringBuilder formattedString = new StringBuilder();
        formattedString.append("Receipt ID: ").append(id).append("\n");
        formattedString.append("Products bought: \n \n");
        for (Product product : productsBought){
            formattedString.append(product.getName()).append("\n");
        }
        formattedString.append("\n");
        formattedString.append(messages.getString("totalCost")+" ").append(String.format("%.2f", totalCost)).append("\n");
        formattedString.append(messages.getString("amountPaid")+" ").append(String.format("%.2f", amountPaid)).append("\n");
        formattedString.append(messages.getString("changeGiven")+" ").append(String.format("%.2f", changeGiven)).append("\n");
        formattedString.append(LocalDateTime.now());
        return formattedString.toString();
    }

}