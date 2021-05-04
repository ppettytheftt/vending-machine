package VendingItems;

public class Chip extends Item {

    public Chip(String slot, String name, double price, int quantity, String message) {
        super(slot, name, price, Category.Chip.name(), quantity, message);
    }
}
