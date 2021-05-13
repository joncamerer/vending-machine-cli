package com.techelevator;

import com.techelevator.view.Menu;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Scanner;

public class VendingMachineCLITest {

    @Test
    public void calculateChangeTest_returns_correct_change_from_one_fifteen() {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI machine = new VendingMachineCLI(menu);

        String actual = machine.calculateChange(new BigDecimal("1.15"));
        String expected = "Quarter(s): 4, Dime(s): 1, Nickel(s): 1";

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void calculateChangeTest_returns_correct_change_from_0() {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI machine = new VendingMachineCLI(menu);

        String actual = machine.calculateChange(new BigDecimal("0"));
        String expected = "Quarter(s): 0, Dime(s): 0, Nickel(s): 0";

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void feedMoneyTest_send_in_5_from_0_and_return_5() {
        ByteArrayInputStream in = new ByteArrayInputStream("5".getBytes());
        System.setIn(in);

        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI machine = new VendingMachineCLI(menu);

        BigDecimal amountTendered = new BigDecimal("0");

        BigDecimal actual = machine.feedMoney(amountTendered);
        BigDecimal expected = new BigDecimal("5");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void feedMoneyTest_send_in_20_from_0_and_return_0() {
        ByteArrayInputStream in = new ByteArrayInputStream("20".getBytes());
        System.setIn(in);

        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI machine = new VendingMachineCLI(menu);

        BigDecimal amountTendered = new BigDecimal("0");

        BigDecimal actual = machine.feedMoney(amountTendered);
        BigDecimal expected = new BigDecimal("0");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void feedMoneyTest_send_in_20_from_5_and_return_5() {
        ByteArrayInputStream in = new ByteArrayInputStream("20".getBytes());
        System.setIn(in);

        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI machine = new VendingMachineCLI(menu);

        BigDecimal amountTendered = new BigDecimal("5");

        BigDecimal actual = machine.feedMoney(amountTendered);
        BigDecimal expected = new BigDecimal("5");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void feedMoneyTest_send_in_orange_from_5_and_return_5() {
        ByteArrayInputStream in = new ByteArrayInputStream("orange".getBytes());
        System.setIn(in);

        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI machine = new VendingMachineCLI(menu);

        BigDecimal amountTendered = new BigDecimal("5");

        BigDecimal actual = machine.feedMoney(amountTendered);
        BigDecimal expected = new BigDecimal("5");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void purchaseProductTest_purchase_B4_return_correct_change() {
        ByteArrayInputStream in = new ByteArrayInputStream("B4".getBytes());
        System.setIn(in);

        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI machine = new VendingMachineCLI(menu);

        Product[] inventory = { new Chip("A3","Grain Waves","2.75"), new Candy("B4", "Crunchie", "1.75")};
        BigDecimal amountTendered = new BigDecimal("5");

        BigDecimal actual =  machine.purchaseProduct(inventory, amountTendered);
        BigDecimal expected = new BigDecimal("3.25");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void purchaseProductTest_purchase_b4_return_correct_change_test_case_sensitive() {
        ByteArrayInputStream in = new ByteArrayInputStream("b4".getBytes());
        System.setIn(in);

        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI machine = new VendingMachineCLI(menu);

        Product[] inventory = { new Chip("A3","Grain Waves","2.75"), new Candy("B4", "Crunchie", "1.75")};
        BigDecimal amountTendered = new BigDecimal("5");

        BigDecimal actual =  machine.purchaseProduct(inventory, amountTendered);
        BigDecimal expected = new BigDecimal("3.25");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void purchaseProductTest_purchase_b4_from_0_return_0_too_expensive() {
        ByteArrayInputStream in = new ByteArrayInputStream("B4".getBytes());
        System.setIn(in);

        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI machine = new VendingMachineCLI(menu);

        Product[] inventory = { new Chip("A3","Grain Waves","2.75"), new Candy("B4", "Crunchie", "1.75")};
        BigDecimal amountTendered = new BigDecimal("0");

        BigDecimal actual =  machine.purchaseProduct(inventory, amountTendered);
        BigDecimal expected = new BigDecimal("0");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void purchaseProductTest_purchase_B4_return_amountTendered_out_of_stock() {
        ByteArrayInputStream in = new ByteArrayInputStream("B4".getBytes());
        System.setIn(in);

        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI machine = new VendingMachineCLI(menu);

        Candy item = new Candy("B4", "Crunchie", "1.75");

        for (int i = 0; i < 5; i++) {
            item.purchase();
        }

        Product[] inventory = { new Chip("A3","Grain Waves","2.75"), item};
        BigDecimal amountTendered = new BigDecimal("5");

        BigDecimal actual =  machine.purchaseProduct(inventory, amountTendered);
        BigDecimal expected = new BigDecimal("5");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void purchaseProductTest_purchase_E4_return_amountTendered_item_does_not_exist() {
        ByteArrayInputStream in = new ByteArrayInputStream("E4".getBytes());
        System.setIn(in);

        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI machine = new VendingMachineCLI(menu);

        Product[] inventory = { new Chip("A3","Grain Waves","2.75"), new Candy("B4", "Crunchie", "1.75")};
        BigDecimal amountTendered = new BigDecimal("5");

        BigDecimal actual =  machine.purchaseProduct(inventory, amountTendered);
        BigDecimal expected = new BigDecimal("5");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void buildInventoryTest_send_in_16_line_inventory_return_Product_array_length_16() {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI machine = new VendingMachineCLI(menu);
        //This path may need to be adjusted if test fails
        String filePath = "C:\\Users\\Student\\workspace\\mod1-wk4-pairs-green-t7\\java\\src\\test\\java\\com\\techelevator\\testinventory.csv";

        Product[] actual = machine.buildInventory(filePath);

        Assert.assertEquals(16, actual.length);
    }

    @Test
    public void buildInventoryTest_send_in_0_line_inventory_return_Product_array_length_0() {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI machine = new VendingMachineCLI(menu);
        //This path may need to be adjusted if test fails
        String filePath = "C:\\Users\\Student\\workspace\\mod1-wk4-pairs-green-t7\\java\\src\\test\\java\\com\\techelevator\\blankinventory.txt";

        Product[] actual = machine.buildInventory(filePath);

        Assert.assertEquals(0, actual.length);
    }

    @Test
    public void initializeLogTest_initializes_a_log() {
        ByteArrayInputStream in = new ByteArrayInputStream("3".getBytes());
        System.setIn(in);


        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI machine = new VendingMachineCLI(menu);

        machine.run();

        String LOG_PATH = "java/Log.txt"; //Path from VendingMachineCLI

        File file = new File(LOG_PATH);
        try {
            Scanner scanner = new Scanner(file);
            int lineCount = 0;

            while (scanner.hasNextLine()) {
                lineCount++;
                String line = scanner.nextLine();
            }
            Assert.assertEquals(1, lineCount);
        } catch (FileNotFoundException e) {
            System.out.println("INVALID FILE PATH");
            System.out.println(e.getMessage());
        }
    }
}