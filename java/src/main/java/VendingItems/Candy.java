package VendingItems;

public class Candy extends Item {

    public Candy(String slot, String name, double price, int quantity, String message) {
        super(slot, name, price, Category.Candy.name(), quantity, message);
    }

}


