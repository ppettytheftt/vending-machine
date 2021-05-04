package VendingItems;

public class Drink extends Item {
    public Drink(String slot, String name, double price, int quantity, String message) {
        super(slot, name, price, Category.Drink.name(), quantity, message);
    }
}
