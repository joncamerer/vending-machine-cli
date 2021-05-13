package com.techelevator;

import com.techelevator.Chip;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ChipTest {

    @Test
    public void ChipConstructorTest_builds_as_expected() {
        Chip actual = new Chip("A1", "Potato Crisps", "3.05");

        BigDecimal price = new BigDecimal("3.05");

        Assert.assertEquals("A1", actual.getSlotLocation());
        Assert.assertEquals("Potato Crisps", actual.getProductName());
        Assert.assertEquals(price, actual.getPrice());
        Assert.assertEquals(5, actual.getQuantity());
    }

    @Test
    public void ChipTest_getMessage() {
        Chip actual = new Chip("A2" ,"Stackers", "1.45");
        Assert.assertEquals("Crunch Crunch, Yum!", actual.getMessage());
    }
}