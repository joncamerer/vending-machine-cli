package com.techelevator;

public class Drink extends Product {

    public Drink(String slotLocation, String productName, String price) {

        super(slotLocation, productName, price);
    }

    @Override
    public String getMessage() {
        return "Glug Glug, Yum!";
    }
}