package foodordering.model;

public class OrderItem {
    private final String foodName;
    private final double unitPrice;
    private final int quantity;

    public OrderItem(String foodName, double unitPrice, int quantity) {
        this.foodName = foodName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }
    public String getFoodName() { return foodName; }
    public double getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
    public double getSubtotal() { return unitPrice * quantity; }
}
