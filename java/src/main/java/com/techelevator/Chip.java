package com.techelevator;

public class Chip extends Product {

    public Chip(String slotLocation, String productName, String price) {

        super(slotLocation, productName, price);
    }

    @Override
    public String getMessage() {
        return "Crunch Crunch, Yum!";
    }
}
