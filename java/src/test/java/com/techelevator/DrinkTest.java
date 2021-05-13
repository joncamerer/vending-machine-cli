package com.techelevator;

import com.techelevator.Drink;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class DrinkTest {

    @Test
    public void DrinkConstructorTest_builds_as_expected() {
        Drink actual = new Drink("A1", "Take 5", ".95");

        BigDecimal price = new BigDecimal(".95");

        Assert.assertEquals("A1", actual.getSlotLocation());
        Assert.assertEquals("Take 5", actual.getProductName());
        Assert.assertEquals(price, actual.getPrice());
        Assert.assertEquals(5, actual.getQuantity());
    }

    @Test
    public void DrinkTest_getMessage() {
        Drink actual = new Drink("C1" ,"Cola", "1.25");
        Assert.assertEquals("Glug Glug, Yum!", actual.getMessage());
    }
}