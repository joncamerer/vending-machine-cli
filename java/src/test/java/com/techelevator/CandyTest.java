package com.techelevator;

import com.techelevator.Candy;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class CandyTest {

    @Test
    public void CandyConstructorTest_builds_as_expected() {
        Candy actual = new Candy("A1", "Take 5", ".95");

        BigDecimal price = new BigDecimal(".95");

        Assert.assertEquals("A1", actual.getSlotLocation());
        Assert.assertEquals("Take 5", actual.getProductName());
        Assert.assertEquals(price, actual.getPrice());
        Assert.assertEquals(5, actual.getQuantity());
    }
    
    @Test
    public void CandyTest_getMessage() {
        Candy actual = new Candy("B1" ,"Moonpie", "1.80");
        Assert.assertEquals("Munch Munch, Yum!", actual.getMessage());
    }
}