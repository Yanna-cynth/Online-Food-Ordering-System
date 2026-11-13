package foodordering.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private final int id;
    private final int customerId;
    private final String customerName;
    private final String deliveryAddress;
    private final List<OrderItem> items;
    private final double total;
    private final LocalDateTime createdAt;
    private OrderStatus status;
    private Payment payment;
    private Delivery delivery;

    public Order(int id, Customer customer, List<OrderItem> items, double total) {
        this.id = id;
        this.customerId = customer.getId();
        this.customerName = customer.getName();
        this.deliveryAddress = customer.getAddress();
        this.items = new ArrayList<>(items);
        this.total = total;
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.PLACED;
    }
    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public List<OrderItem> getItems() { return Collections.unmodifiableList(items); }
    public double getTotal() { return total; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public OrderStatus getStatus() { return status; }
    public Payment getPayment() { return payment; }
    public Delivery getDelivery() { return delivery; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public void setPayment(Payment payment) { this.payment = payment; }
    public void setDelivery(Delivery delivery) { this.delivery = delivery; }
}
