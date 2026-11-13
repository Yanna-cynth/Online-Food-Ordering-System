package foodordering.model;

public class CartItem {
    private final Food food;
    private int quantity;

    public CartItem(Food food, int quantity) {
        this.food = food;
        setQuantity(quantity);
    }
    public Food getFood() { return food; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("Quantity must be at least 1.");
        this.quantity = quantity;
    }
    public double getSubtotal() { return food.getPrice() * quantity; }
}
