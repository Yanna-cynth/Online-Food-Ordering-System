package foodordering.service;

import foodordering.model.*;
import java.util.ArrayList;
import java.util.List;

public class FoodOrderingSystem {
    private final List<User> users = new ArrayList<>();
    private final List<Food> foods = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();
    private int nextUserId = 1, nextFoodId = 1, nextOrderId = 1, nextPaymentId = 1, nextDeliveryId = 1;

    public FoodOrderingSystem() {
        users.add(new Admin(nextUserId++, "System Administrator", "admin", "admin123", "0000000000"));
        seedFood("Classic Burger", "Beef patty, lettuce and cheese", 99.00);
        seedFood("Cheese Pizza", "Personal-sized cheese pizza", 149.00);
        seedFood("Chicken Meal", "Fried chicken with rice", 125.00);
        seedFood("French Fries", "Crispy salted fries", 59.00);
        seedFood("Iced Tea", "Refreshing iced tea", 35.00);
    }
    private void seedFood(String name, String description, double price) {
        foods.add(new Food(nextFoodId++, name, description, price));
    }

    public User login(String username, String password) {
        return users.stream().filter(u -> u.getUsername().equalsIgnoreCase(username) && u.checkPassword(password))
                .findFirst().orElse(null);
    }
    public Customer registerCustomer(String name, String username, String password, String phone, String address) {
        if (name.isBlank() || username.isBlank() || password.isBlank() || address.isBlank())
            throw new IllegalArgumentException("Name, username, password and address are required.");
        if (users.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username)))
            throw new IllegalArgumentException("That username is already taken.");
        Customer customer = new Customer(nextUserId++, name, username, password, phone, address);
        users.add(customer);
        return customer;
    }
    public List<Food> getFoods() { return foods; }
    public List<User> getUsers() { return users; }
    public List<Order> getOrders() { return orders; }
    public List<Food> getAvailableFoods() {
        return foods.stream().filter(Food::isAvailable).toList();
    }
    public Food findFood(int id) {
        return foods.stream().filter(f -> f.getId() == id).findFirst().orElse(null);
    }
    public Food addFood(String name, String description, double price) {
        Food food = new Food(nextFoodId++, name, description, price);
        foods.add(food);
        return food;
    }
    public void updateFoodPrice(int id, double price) {
        Food food = findFood(id);
        if (food == null) throw new IllegalArgumentException("Food item not found.");
        food.setPrice(price);
    }
    public void setFoodAvailability(int id, boolean available) {
        Food food = findFood(id);
        if (food == null) throw new IllegalArgumentException("Food item not found.");
        food.setAvailable(available);
    }
    public Order placeOrder(Customer customer, Cart cart, PaymentMethod method) {
        if (cart.isEmpty()) throw new IllegalArgumentException("Your cart is empty.");
        List<OrderItem> snapshot = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            if (!item.getFood().isAvailable()) throw new IllegalArgumentException(item.getFood().getName() + " is unavailable.");
            snapshot.add(new OrderItem(item.getFood().getName(), item.getFood().getPrice(), item.getQuantity()));
        }
        Order order = new Order(nextOrderId++, customer, snapshot, cart.getTotal());
        Payment payment = new Payment(nextPaymentId++, method, order.getTotal(), true);
        order.setPayment(payment);
        orders.add(order);
        cart.clear();
        return order;
    }
    public Order findOrder(int id) {
        return orders.stream().filter(o -> o.getId() == id).findFirst().orElse(null);
    }
    public void updateOrderStatus(int id, OrderStatus status) {
        Order order = findOrder(id);
        if (order == null) throw new IllegalArgumentException("Order not found.");
        order.setStatus(status);
    }
    public Delivery assignDelivery(int orderId, String courier, String phone, String notes) {
        Order order = findOrder(orderId);
        if (order == null) throw new IllegalArgumentException("Order not found.");
        Delivery delivery = new Delivery(nextDeliveryId++, courier, phone, notes);
        order.setDelivery(delivery);
        if (order.getStatus() == OrderStatus.READY_FOR_PICKUP)
            order.setStatus(OrderStatus.OUT_FOR_DELIVERY);
        return delivery;
    }
    public double getTotalSales() {
        return orders.stream().filter(o -> o.getStatus() != OrderStatus.CANCELLED)
                .mapToDouble(Order::getTotal).sum();
    }
}
