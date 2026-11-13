package foodordering.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
    private final List<CartItem> items = new ArrayList<>();

    public void addFood(Food food, int quantity) {
        for (CartItem item : items) {
            if (item.getFood().getId() == food.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(food, quantity));
    }
    public void removeFood(int foodId) {
        items.removeIf(item -> item.getFood().getId() == foodId);
    }
    public void clear() { items.clear(); }
    public boolean isEmpty() { return items.isEmpty(); }
    public List<CartItem> getItems() { return Collections.unmodifiableList(items); }
    public double getTotal() {
        return items.stream().mapToDouble(CartItem::getSubtotal).sum();
    }
}
