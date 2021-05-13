package com.techelevator;

import com.techelevator.Chip;
import org.junit.Assert;
import org.junit.Test;

public class ProductTest {

    @Test
    public void testToString() {
        Product actual = new Chip("A1", "Potato Crisps", "3.05");
        Assert.assertEquals("A1) Potato Crisps        | $3.05 | 5 in stock", actual.toString());
    }

    @Test
    public void productToStringTest_prints_OUT_OF_STOCK() {
        Product actual = new Chip("A1", "Potato Crisps", "3.05");
        for (int i = 0; i < 5; i++) {
            actual.purchase();
        }

        Assert.assertEquals("A1) Potato Crisps        | $3.05 | OUT OF STOCK", actual.toString());

    }

    @Test
    public void productGetMessageTest_can_get_a_message() {
        Product actual = new Chip("A2" ,"Stackers", "1.45");
        Assert.assertEquals("Crunch Crunch, Yum!", actual.getMessage());
    }
}