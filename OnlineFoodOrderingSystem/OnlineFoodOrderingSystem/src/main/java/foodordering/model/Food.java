package foodordering.model;

public class Food {
    private final int id;
    private String name;
    private String description;
    private double price;
    private boolean available;

    public Food(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        setPrice(price);
        this.available = true;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return available; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative.");
        this.price = price;
    }
    public void setAvailable(boolean available) { this.available = available; }

    @Override public String toString() {
        return String.format("%-4d %-22s ₱%8.2f  %s", id, name, price, available ? "Available" : "Unavailable");
    }
}
