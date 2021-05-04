package com.techelevator;

import VendingItems.Item;
import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Purchase {

    private double currentBalance;


    public Purchase(double currentBalance) {
        this.currentBalance = (currentBalance);
    }

    public double addMoney(int billAdded) {
        switch (billAdded) {
            case 1:
                currentBalance += 1;
                break;
            case 2:
                currentBalance += 2;
                break;
            case 5:
                currentBalance += 5;
                break;
            case 10:
                currentBalance += 10;
                break;
            default:
                System.out.println("Not a valid bill, this machine accepts $1, $2, $5, and $10 bills. Your current balance is:\n" + getCurrentBalance());

        }
        return currentBalance;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public boolean makePurchase(Item item) {
        int newQuantity = 0;
        if (getCurrentBalance() >= item.getPrice() && item.getQuantity() >= 1){
            String message = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " " + item.getName() + " " + item.getSlot() + " $" + currentBalance;
            currentBalance = currentBalance - item.getPrice();
            message = message + " " + currentBalance;
            newQuantity = item.getQuantity() - 1;
            item.setQuantity(newQuantity);
            Logger logFile = new Logger("Log.txt");
            logFile.write (message);
            return true;
        } else if(item.getQuantity()< 1) {
                System.out.println("Sold out! Please make another selection!");
            } else {
                System.out.println("Not enough money! Please enter more money.");
            }return false;
        }



    public HashMap<String, Integer> getChange(){
        HashMap<String, Integer> change = new HashMap<>();

        double coins = currentBalance * 100;
        int quarters = (int)(coins/25);
        coins %= 25;
        change.put("quarters", quarters);
        int dimes = (int)(coins/10);
        coins %= 10;
        change.put("dimes", dimes);
        int nickles = (int)(coins/5);
        change.put("nickles", nickles);

        currentBalance = 0;

        return change;


    }
}

