package com.techelevator;

import com.techelevator.Gum;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class GumTest {

    @Test
    public void GumConstructorTest_builds_as_expected() {
        Gum actual = new Gum("A1", "Take 5", ".95");

        BigDecimal price = new BigDecimal(".95");

        Assert.assertEquals("A1", actual.getSlotLocation());
        Assert.assertEquals("Take 5", actual.getProductName());
        Assert.assertEquals(price, actual.getPrice());
        Assert.assertEquals(5, actual.getQuantity());
    }

    @Test
    public void GumTest_getMessage() {
        Gum actual = new Gum("D4" ,"Triplemint", ".75");
        Assert.assertEquals("Chew Chew, Yum!", actual.getMessage());
    }
}