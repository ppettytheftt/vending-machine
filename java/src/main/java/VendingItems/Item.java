package VendingItems;

public abstract class Item {

    private String slot;
    private String name;
    private double price;
    private String category;
    private int quantity;
    private String message;



    public Item (String slot, String name, Double price, String category, int quantity, String message) {
        this.slot = slot;
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
        this.message = message;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }


    public double getPrice() {
        return price;
    }

    public String getMessage() { return this.message; }
}

