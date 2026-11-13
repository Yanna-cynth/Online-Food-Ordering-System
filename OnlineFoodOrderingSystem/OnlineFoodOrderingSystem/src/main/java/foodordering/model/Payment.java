package foodordering.model;

import java.time.LocalDateTime;

public class Payment {
    private final int id;
    private final PaymentMethod method;
    private final double amount;
    private final LocalDateTime paidAt;
    private final boolean successful;

    public Payment(int id, PaymentMethod method, double amount, boolean successful) {
        this.id = id;
        this.method = method;
        this.amount = amount;
        this.successful = successful;
        this.paidAt = LocalDateTime.now();
    }
    public int getId() { return id; }
    public PaymentMethod getMethod() { return method; }
    public double getAmount() { return amount; }
    public LocalDateTime getPaidAt() { return paidAt; }
    public boolean isSuccessful() { return successful; }
}
