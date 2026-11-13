package foodordering.ui;

import foodordering.model.*;
import foodordering.service.FoodOrderingSystem;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    private final FoodOrderingSystem system;
    private final Scanner input = new Scanner(System.in);

    public ConsoleApp(FoodOrderingSystem system) { this.system = system; }

    public void run() {
        System.out.println("==================================");
        System.out.println("     ONLINE FOOD ORDERING SYSTEM");
        System.out.println("==================================");
        boolean running = true;
        while (running) {
            System.out.println("\n1. Login\n2. Register as customer\n0. Exit");
            int choice = readInt("Choose: ");
            switch (choice) {
                case 1 -> login();
                case 2 -> register();
                case 0 -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }
        System.out.println("Thank you for using the system!");
    }

    private void login() {
        String username = readLine("Username: ");
        String password = readLine("Password: ");
        User user = system.login(username, password);
        if (user == null) {
            System.out.println("Invalid username or password.");
        } else if (user instanceof Admin admin) {
            adminMenu(admin);
        } else if (user instanceof Customer customer) {
            customerMenu(customer);
        }
    }

    private void register() {
        try {
            String name = readLine("Full name: ");
            String username = readLine("Choose username: ");
            String password = readLine("Choose password: ");
            String phone = readLine("Phone number: ");
            String address = readLine("Delivery address: ");
            Customer customer = system.registerCustomer(name, username, password, phone, address);
            System.out.println("Account created. Welcome, " + customer.getName() + "!");
        } catch (IllegalArgumentException ex) {
            System.out.println("Registration failed: " + ex.getMessage());
        }
    }

    private void customerMenu(Customer customer) {
        Cart cart = new Cart();
        boolean active = true;
        while (active) {
            System.out.println("\n--- CUSTOMER MENU (" + customer.getName() + ") ---");
            System.out.println("1. Browse food menu\n2. Add food to cart\n3. View cart\n4. Remove item from cart");
            System.out.println("5. Checkout\n6. Track my orders\n7. Update my profile\n0. Logout");
            switch (readInt("Choose: ")) {
                case 1 -> showFoods(system.getAvailableFoods());
                case 2 -> addToCart(cart);
                case 3 -> showCart(cart);
                case 4 -> removeFromCart(cart);
                case 5 -> checkout(customer, cart);
                case 6 -> showCustomerOrders(customer);
                case 7 -> updateProfile(customer);
                case 0 -> active = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void showFoods(List<Food> foods) {
        System.out.println("\nID   FOOD                   PRICE       STATUS");
        for (Food food : foods) System.out.println(food);
        if (foods.isEmpty()) System.out.println("No available food items.");
    }

    private void addToCart(Cart cart) {
        showFoods(system.getAvailableFoods());
        int id = readInt("Food ID (0 to cancel): ");
        if (id == 0) return;
        Food food = system.findFood(id);
        if (food == null || !food.isAvailable()) {
            System.out.println("Food item not found or unavailable.");
            return;
        }
        int quantity = readInt("Quantity: ");
        if (quantity < 1) { System.out.println("Quantity must be at least 1."); return; }
        cart.addFood(food, quantity);
        System.out.println("Added to cart.");
    }

    private void showCart(Cart cart) {
        System.out.println("\n--- YOUR CART ---");
        if (cart.isEmpty()) { System.out.println("Your cart is empty."); return; }
        for (CartItem item : cart.getItems())
            System.out.printf("%-22s x%-3d ₱%.2f%n", item.getFood().getName(), item.getQuantity(), item.getSubtotal());
        System.out.printf("TOTAL: ₱%.2f%n", cart.getTotal());
    }

    private void removeFromCart(Cart cart) {
        showCart(cart);
        if (cart.isEmpty()) return;
        int id = readInt("Enter food ID to remove: ");
        cart.removeFood(id);
        System.out.println("If that item was in your cart, it has been removed.");
    }

    private void checkout(Customer customer, Cart cart) {
        showCart(cart);
        if (cart.isEmpty()) return;
        System.out.println("Payment method:\n1. Cash on delivery\n2. E-wallet (simulated)\n3. Card (simulated)");
        PaymentMethod method = switch (readInt("Choose: ")) {
            case 1 -> PaymentMethod.CASH_ON_DELIVERY;
            case 2 -> PaymentMethod.E_WALLET;
            case 3 -> PaymentMethod.CARD;
            default -> null;
        };
        if (method == null) { System.out.println("Invalid payment method."); return; }
        try {
            Order order = system.placeOrder(customer, cart, method);
            System.out.printf("Order #%d placed successfully. Total: ₱%.2f%n", order.getId(), order.getTotal());
            System.out.println("Status: " + order.getStatus());
        } catch (IllegalArgumentException ex) {
            System.out.println("Checkout failed: " + ex.getMessage());
        }
    }

    private void showCustomerOrders(Customer customer) {
        boolean found = false;
        for (Order order : system.getOrders()) {
            if (order.getCustomerId() == customer.getId()) {
                printOrder(order);
                found = true;
            }
        }
        if (!found) System.out.println("You have no orders yet.");
    }

    private void updateProfile(Customer customer) {
        customer.setName(readLine("New display name: "));
        customer.setPhone(readLine("New phone number: "));
        customer.setAddress(readLine("New delivery address: "));
        System.out.println("Profile updated.");
    }

    private void adminMenu(Admin admin) {
        boolean active = true;
        while (active) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. View food menu\n2. Add food item\n3. Update food price\n4. Toggle food availability");
            System.out.println("5. View all orders\n6. Update order status\n7. Assign courier\n8. View users\n9. Sales report\n0. Logout");
            switch (readInt("Choose: ")) {
                case 1 -> showFoods(system.getFoods());
                case 2 -> addFood();
                case 3 -> updateFoodPrice();
                case 4 -> toggleAvailability();
                case 5 -> showAllOrders();
                case 6 -> updateOrderStatus();
                case 7 -> assignCourier();
                case 8 -> showUsers();
                case 9 -> salesReport();
                case 0 -> active = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void addFood() {
        String name = readLine("Food name: ");
        String description = readLine("Description: ");
        double price = readDouble("Price: ");
        try {
            Food food = system.addFood(name, description, price);
            System.out.println("Added: " + food);
        } catch (IllegalArgumentException ex) { System.out.println(ex.getMessage()); }
    }

    private void updateFoodPrice() {
        int id = readInt("Food ID: ");
        double price = readDouble("New price: ");
        try { system.updateFoodPrice(id, price); System.out.println("Price updated."); }
        catch (IllegalArgumentException ex) { System.out.println(ex.getMessage()); }
    }

    private void toggleAvailability() {
        int id = readInt("Food ID: ");
        Food food = system.findFood(id);
        if (food == null) { System.out.println("Food item not found."); return; }
        system.setFoodAvailability(id, !food.isAvailable());
        System.out.println("Availability is now " + (food.isAvailable() ? "Available" : "Unavailable"));
    }

    private void showAllOrders() {
        if (system.getOrders().isEmpty()) { System.out.println("No orders yet."); return; }
        for (Order order : system.getOrders()) printOrder(order);
    }

    private void updateOrderStatus() {
        int id = readInt("Order ID: ");
        System.out.println("1. PLACED  2. PREPARING  3. READY_FOR_PICKUP  4. OUT_FOR_DELIVERY  5. DELIVERED  6. CANCELLED");
        OrderStatus status = switch (readInt("New status: ")) {
            case 1 -> OrderStatus.PLACED;
            case 2 -> OrderStatus.PREPARING;
            case 3 -> OrderStatus.READY_FOR_PICKUP;
            case 4 -> OrderStatus.OUT_FOR_DELIVERY;
            case 5 -> OrderStatus.DELIVERED;
            case 6 -> OrderStatus.CANCELLED;
            default -> null;
        };
        if (status == null) { System.out.println("Invalid status."); return; }
        try { system.updateOrderStatus(id, status); System.out.println("Order status updated."); }
        catch (IllegalArgumentException ex) { System.out.println(ex.getMessage()); }
    }

    private void assignCourier() {
        int id = readInt("Order ID: ");
        String name = readLine("Courier name: ");
        String phone = readLine("Courier phone: ");
        String notes = readLine("Delivery notes: ");
        try {
            system.assignDelivery(id, name, phone, notes);
            System.out.println("Courier assigned.");
        } catch (IllegalArgumentException ex) { System.out.println(ex.getMessage()); }
    }

    private void showUsers() {
        for (User user : system.getUsers())
            System.out.printf("ID: %d | %-20s | %-12s | %s%n", user.getId(), user.getName(), user.getUsername(), user.getRole());
    }

    private void salesReport() {
        long count = system.getOrders().stream().filter(o -> o.getStatus() != OrderStatus.CANCELLED).count();
        System.out.println("\n--- SALES REPORT ---");
        System.out.println("Non-cancelled orders: " + count);
        System.out.printf("Recorded sales: ₱%.2f%n", system.getTotalSales());
    }

    private void printOrder(Order order) {
        System.out.printf("%nOrder #%d | Customer: %s | Status: %s | Total: ₱%.2f%n",
                order.getId(), order.getCustomerName(), order.getStatus(), order.getTotal());
        System.out.println("Address: " + order.getDeliveryAddress());
        for (OrderItem item : order.getItems())
            System.out.printf("  %s x%d = ₱%.2f%n", item.getFoodName(), item.getQuantity(), item.getSubtotal());
        if (order.getPayment() != null)
            System.out.println("Payment: " + order.getPayment().getMethod() + " | " + (order.getPayment().isSuccessful() ? "Recorded" : "Failed"));
        if (order.getDelivery() != null)
            System.out.println("Courier: " + order.getDelivery().getCourierName() + " | Phone: " + order.getDelivery().getCourierPhone());
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return input.nextLine().trim();
    }
    private int readInt(String prompt) {
        while (true) {
            try { return Integer.parseInt(readLine(prompt)); }
            catch (NumberFormatException ex) { System.out.println("Please enter a whole number."); }
        }
    }
    private double readDouble(String prompt) {
        while (true) {
            try {
                double value = Double.parseDouble(readLine(prompt));
                if (Double.isFinite(value)) return value;
            } catch (NumberFormatException ignored) { }
            System.out.println("Please enter a valid number.");
        }
    }
}
