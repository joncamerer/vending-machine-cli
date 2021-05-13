package com.techelevator;

import java.math.BigDecimal;
import java.text.NumberFormat;

public abstract class Product {
    private String slotLocation;
    private String productName;
    private BigDecimal price;
    private int quantity;

    public Product(String slotLocation, String productName, String price) {

        this.slotLocation = slotLocation;
        this.productName = productName;
        this.price = new BigDecimal(price);
        this.quantity = 5;
    }

    public String getSlotLocation() {
        return slotLocation;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void purchase() {
        this.quantity -= 1;
    }

    public abstract String getMessage();

    public String toString() {
        String inStock = quantity <= 0? "OUT OF STOCK" : String.format("%d in stock", quantity);
        return String.format("%s) %-20s | %s | %s", slotLocation, productName, NumberFormat.getCurrencyInstance().format(price), inStock);
    }
}
