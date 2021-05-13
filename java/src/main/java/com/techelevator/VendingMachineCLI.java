package com.techelevator;

import com.techelevator.view.*;

import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class VendingMachineCLI {

	private static final String INVENTORY_PATH = "java/vendingmachine.csv";
	private static final String LOG_PATH = "java/Log.txt";
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_OPTION_PRINT_SALES_REPORT = "Print Sales Report";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_PRINT_SALES_REPORT };
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String PURCHASE_MENU_OPTION_PRODUCT_DETAILS = "Product Details";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT,
			PURCHASE_MENU_OPTION_FINISH_TRANSACTION, PURCHASE_MENU_OPTION_PRODUCT_DETAILS };

	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {
		initializeLog();
		Product[] inventory = buildInventory(INVENTORY_PATH);

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				for (Product product : inventory) {
					System.out.println(product.toString());
				}
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				runPurchaseMenu(inventory);
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				System.out.println("Goodbye...");
				break;
			} else if (choice.equals(MAIN_MENU_OPTION_PRINT_SALES_REPORT)) {
				writeToSalesReport(inventory);
			}
		}
	}

	public void runPurchaseMenu(Product[] inventory) {
		BigDecimal amountTendered = new BigDecimal(0.00);

		while (true) {
			System.out.println("\nCurrent Money Provided: " + NumberFormat.getCurrencyInstance().format(amountTendered));
			String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

			if (choice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
				// Feed money
				amountTendered = this.feedMoney(amountTendered);

			} else if (choice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
				// Do purchase
				amountTendered = purchaseProduct(inventory, amountTendered);  // pass in amountTendered/return amount tendered

			} else if (choice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
				// Give change
				System.out.println("Dispensing Change: " + calculateChange(amountTendered));
				break;
			} else if (choice.equals(PURCHASE_MENU_OPTION_PRODUCT_DETAILS)) {
				System.out.println("99% Zombie Meat Free Guaranteed!");
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}

	public static Product[] buildInventory(String filePath) {
		List<Product> inventory = new ArrayList<>();
		File inventoryFile = new File(filePath);

		try (Scanner inventoryInput = new Scanner(inventoryFile)) {

			while (inventoryInput.hasNextLine()) {
				String thisLine = inventoryInput.nextLine();
				String[] linePieces = thisLine.split("\\|");

				Product item = null;

				switch (linePieces[3]) {
					case "Chip" :
						item = new Chip(linePieces[0], linePieces[1], linePieces[2]);
						break;
					case "Candy":
						item = new Candy(linePieces[0], linePieces[1], linePieces[2]);
						break;
					case "Drink":
						item = new Drink(linePieces[0], linePieces[1], linePieces[2]);
						break;
					case "Gum" :
						item = new Gum(linePieces[0], linePieces[1], linePieces[2]);
				}
				inventory.add(item);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return inventory.toArray(new Product[0]);
	}

	public String calculateChange(BigDecimal amountTendered) {
		int amountRemaining = amountTendered.multiply(new BigDecimal(100)).intValue();

		int amountOfQuarters = amountRemaining / 25;
		amountRemaining = amountRemaining % 25;

		int amountOfDimes = amountRemaining / 10;
		amountRemaining = amountRemaining % 10;

		int amountOfNickels = amountRemaining / 5;


		writeToLog("GIVE CHANGE:", amountTendered, new BigDecimal(0));
		return "Quarter(s): " + amountOfQuarters + ", Dime(s): " + amountOfDimes + ", Nickel(s): " + amountOfNickels;
	}

	public BigDecimal feedMoney(BigDecimal amountTendered) {
		Scanner userInput = new Scanner(System.in);
		List<String> possibleAmounts = List.of("1", "2", "5", "10");
		BigDecimal total = amountTendered;
		System.out.println("Please enter one of the following dollar amounts: 1, 2, 5, or 10");
		String inputString = userInput.nextLine();

		if (possibleAmounts.contains(inputString)) {
			BigDecimal input = new BigDecimal(inputString);
			total = total.add(input);
		} else {
			System.out.println("Dollar amount not accepted.");
		}

		writeToLog("FEED MONEY", amountTendered, total);
		return total;
	}

	public BigDecimal purchaseProduct(Product[] inventory, BigDecimal amountTendered) {
		Scanner userInput = new Scanner(System.in);
		List<String> slotChoices = new ArrayList<>();
		BigDecimal total = amountTendered;

		for (Product product : inventory) {
			System.out.println(product.toString());
			slotChoices.add(product.getSlotLocation());
		}

		System.out.print("Please pick a item: ");
		String choice = userInput.nextLine();
		choice = choice.toUpperCase();
		Product item = null;
		String errorMsg = "";

		if (slotChoices.contains(choice)) {
			int index = slotChoices.indexOf(choice);
			if (inventory[index].getQuantity() > 0) {
				item = inventory[index];
				if (total.compareTo(item.getPrice()) != -1) {
					total = total.subtract(item.getPrice());
					item.purchase();

					System.out.printf("\n%s costs %s. Money remaining: %s\n",item.getProductName(), NumberFormat.getCurrencyInstance().format(item.getPrice()), NumberFormat.getCurrencyInstance().format(total));
					System.out.println(item.getMessage());
				} else {
					errorMsg = "INSUFFICIENT FUNDS";
					System.out.println("Please insert additional funds");
				}
			} else {
				errorMsg = "OUT OF STOCK";
				System.out.println("Item out of stock");
			}
		} else {
			errorMsg = "UNKNOWN";
			System.out.println("Item does not exist");
		}

		String logString = !errorMsg.equals("") ? String.format("%s %s", errorMsg, choice) : String.format("%s %s", item.getProductName(), item.getSlotLocation());
		writeToLog(logString, amountTendered, total);

		return total;
	}

	private void initializeLog () {
		File logFile = new File(LOG_PATH);
		try (PrintWriter logger = new PrintWriter(logFile)) {
			LocalDateTime dateTime = LocalDateTime.now();
			DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss a");
			String formattedDateTime = dateTime.format(dateTimeFormat);
			logger.println(formattedDateTime + " Umbrella Corp. Vendo-Matic 800 Startup");

			logger.flush();
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}

	private void writeToLog(String logType, BigDecimal originalMoney, BigDecimal currentMoney ) {
		File logFile = new File(LOG_PATH);
		try (FileWriter logger = new FileWriter(logFile, true)) {
			LocalDateTime dateTime = LocalDateTime.now();
			DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss a");
			String formattedDateTime = dateTime.format(dateTimeFormat);
			String originalMoneyString = NumberFormat.getCurrencyInstance().format(originalMoney);
			String currentMoneyString = NumberFormat.getCurrencyInstance().format(currentMoney);

			logger.append(formattedDateTime + " " + logType + " " + originalMoneyString + " " + currentMoneyString + "\n");

			logger.flush();
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
	}

	private void writeToSalesReport(Product[] inventory) {
		String basePath = "java/";
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM_dd_yyyy_HH_mm_ss_a");
		String formattedDateTime = dateTime.format(dateTimeFormat);

		File saleReportFile = new File(basePath + formattedDateTime + "_SALES_REPORT.txt");
		BigDecimal totalSales = new BigDecimal(0);

		try (PrintWriter writer = new PrintWriter(saleReportFile)) {
			for (Product product: inventory) {
				int numberSold = 5 - product.getQuantity();
				String line = String.format("%s | %d", product.getProductName(), numberSold);
				writer.println(line);
				BigDecimal productSales = (product.getPrice()).multiply(new BigDecimal(numberSold));
				totalSales = totalSales.add(productSales);
			}

			writer.println("\n" + "TOTAL SALES: " + totalSales);

			writer.flush();
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
}
