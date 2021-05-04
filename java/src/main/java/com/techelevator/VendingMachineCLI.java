package com.techelevator;

import VendingItems.*;
import com.techelevator.view.Menu;
import jdk.swing.interop.SwingInterOpUtils;


import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class VendingMachineCLI {

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};
    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
    private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
    private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};
    private static final String CANDY_MESSAGE = "Munch Munch, Yum!";
    private static final String CHIP_MESSAGE = "Crunch Crunch, Yum!";
    private static final String DRINK_MESSAGE = "Glug Glug, Yum!";
    private static final String GUM_MESSAGE = "Chew Chew, Yum!";

    //add the purchase menu menu items here
    private Menu menu;
    private List<Item> vendinglist = new ArrayList<>();
    private Purchase purchase = new Purchase(0.00);

    Scanner userInput = new Scanner(System.in);

    public VendingMachineCLI(Menu menu) {
        this.menu = menu;
    }

    public void run() { //think of this as the main, read in from the file
        //READ IN FROM THE file
        File itemFile = new File("java/vendingmachine.csv");
        try (Scanner fileScanner = new Scanner(itemFile)) {
            while (fileScanner.hasNextLine()) {
                String fileLine = fileScanner.nextLine();
                String[] fileLineArray = fileLine.split("\\|");
                String slot = fileLineArray[0];
                String name = fileLineArray[1];
                double price = Double.parseDouble(fileLineArray[2]);
                String category = fileLineArray[3];

                switch (category) {
                    case "Chip":
                        vendinglist.add(new Chip(slot, name, price, 5, CHIP_MESSAGE));
                        break;
                    case "Gum":
                        vendinglist.add(new Gum(slot, name, price, 5, GUM_MESSAGE));
                        break;
                    case "Candy":
                        vendinglist.add(new Candy(slot, name, price, 5, CANDY_MESSAGE));
                        break;
                    case "Drink":
                        vendinglist.add(new Drink(slot, name, price, 5, DRINK_MESSAGE));
                        break;
                    default:
                        System.out.println("Not a valid item!");
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        boolean stayOnLevel = true;
        do {
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);


            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                for (Item item : vendinglist) {
                    System.out.println(item.getSlot() + " " + item.getName() + " $" + (String.format("%.2f" , item.getPrice())) + "  " + item.getQuantity() + " Remaining");
                }
            } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {

                displayPurchaseOptions();

            } else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
                System.exit(0);

            } else {
                System.out.println("Not a valid option please try again!");
            }
        } while (stayOnLevel);
    }

    public void displayPurchaseOptions() {
        boolean stayOnLevel = true;

        do{
                String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
                if (choice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
                    System.out.println("Feed money into the machine in valid, whole dollar amounts (e.g. $1, $2, $5, or $10) >>>");
                    if (choice.equals("Feed Money")) {
                        String message = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " Feed Money: $" + purchase.getCurrentBalance();
                        int billAdded = Integer.parseInt(userInput.nextLine());
                        purchase.addMoney(billAdded);
                        message = message + " $" + (String.format("%.2f" , purchase.getCurrentBalance()));
                        Logger logFile = new Logger("Log.txt");
                        logFile.write (message);
                        System.out.println("\nCurrent Money Provided: $" + (String.format("%.2f" , purchase.getCurrentBalance())));
                    }
                } else if (choice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
                    for (Item item : vendinglist) {
                        System.out.println(item.getSlot() + " " + item.getName() + " $" + (String.format("%.2f" , item.getPrice())) + "  " + item.getQuantity() + "   Remaining");
                    }
                    System.out.println("\nPlease make a selection >>>");
                    String selectionID = userInput.nextLine();

                    boolean isFound = false;
                    for (Item item : vendinglist) {
                        if (item.getSlot().equalsIgnoreCase(selectionID)) {

                            boolean isEnoughMoney = purchase.makePurchase(item);
                            if (isEnoughMoney) {
                                System.out.println("Dispensing: " + selectionID + " " + item.getName() + " $" + (String.format("%.2f" , item.getPrice())));
                                System.out.println("Remaining balance: $" + (String.format("%.2f" , purchase.getCurrentBalance())));
                                System.out.println(item.getMessage());
                            }
                            isFound = true;

                        }
                    }
                    if (!isFound) {
                        System.out.println("Invalid Item");
                    }
                } else if (choice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
                    stayOnLevel = false;
                    String message = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " Give change" + purchase.getCurrentBalance();
                    HashMap<String, Integer> change = purchase.getChange();
                    message = message +" " + (String.format("%.2f" , purchase.getCurrentBalance()));
                    Logger logFile = new Logger("Log.txt");
                    logFile.write (message);
                    System.out.println("Your change is: " + change);
                    System.out.println("Thank you for your purchase!");

                    System.exit(0);

                }

        }while(stayOnLevel);
    }

    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();
    }

}
