package VendingItems;

public class Gum extends Item {
    public Gum(String slot, String name, double price, int quantity, String message) {
        super(slot, name, price, Category.Gum.name(), quantity, message);
    }
}
