package com.techelevator.view;

import VendingItems.Category;
import com.techelevator.Purchase;
import VendingItems.Candy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.util.HashMap;

public class PurchaseTest {

    @Test
    public void returns_correct_change_getChange() {
        Purchase purchase = new Purchase(1.15);
        HashMap<String, Integer> results = purchase.getChange();

        Assert.assertEquals(4, results.get("quarters").intValue());
        Assert.assertEquals(1, results.get("dimes").intValue());
        Assert.assertEquals(1, results.get("nickles").intValue());
    }

    @Test
    public void addMoney_billAdded_$1_update_balance_$1() {
        Purchase purchase = new Purchase(0);
        double actual = purchase.addMoney(1);
        double expected = 1;
        Assert.assertEquals( expected,  actual, 0.1);
    }
    @Test
    public void addMoney_billAdded_$2_update_balance_$2() {
        Purchase purchase = new Purchase(0);
        double actual = purchase.addMoney(2);
        double expected = 2;
        Assert.assertEquals( expected,  actual,  .01);
    }
    @Test
    public void addMoney_billAdded_$5_update_balance_$5() {
        Purchase purchase = new Purchase(0);
        double actual = purchase.addMoney(5);
        double expected = 5;
        Assert.assertEquals( expected,  actual,  .01);
    }
    @Test
    public void addMoney_billAdded_$10_update_balance_$10() {
        Purchase purchase = new Purchase(0);
        double actual = purchase.addMoney(10);
        double expected = 10;
        Assert.assertEquals( expected,  actual,  .01);
    }

    @Test
    public void addMoney_billAdded_$3_update_balance_not_valid() {
        Purchase purchase = new Purchase(1);
        double actual = purchase.addMoney(3);
        double expected = 0;
        Assert.assertNotEquals(expected,  actual,  .01);
    }

    @Test
    public void getCurrentBalance() {
        Purchase purchase = new Purchase(10);
        Assert.assertEquals(10, purchase.getCurrentBalance(), .01);
    }

    @Test
    public void makePurchase_currentBalance_$1_Item_75() {
        Purchase purchase = new Purchase(1);
        var item = new Candy("A1", "La Fin Du Monde", 0.75, 5, "At Worlds End");
        Assert.assertTrue(purchase.makePurchase(item));
    }

    @Test
    public void makePurchase_currentBalance_$0_Item_75() {
        Purchase purchase = new Purchase(0);
        var item = new Candy("A1", "La Fin Du Monde", 0.75, 5, "At Worlds End");
        Assert.assertFalse(purchase.makePurchase(item));
    }

    @Test
    public void makePurchase_currentBalance_$1_Item_75_quantity_0() {
        Purchase purchase = new Purchase(1);
        var item = new Candy("A1", "La Fin Du Monde", 0.75, 0, "At Worlds End");
        Assert.assertFalse(purchase.makePurchase(item));
    }
}
