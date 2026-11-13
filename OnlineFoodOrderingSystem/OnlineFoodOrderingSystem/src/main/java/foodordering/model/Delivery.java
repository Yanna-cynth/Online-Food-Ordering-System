package foodordering.model;

public class Delivery {
    private final int id;
    private String courierName;
    private String courierPhone;
    private String notes;

    public Delivery(int id, String courierName, String courierPhone, String notes) {
        this.id = id;
        this.courierName = courierName;
        this.courierPhone = courierPhone;
        this.notes = notes;
    }
    public int getId() { return id; }
    public String getCourierName() { return courierName; }
    public String getCourierPhone() { return courierPhone; }
    public String getNotes() { return notes; }
    public void assignCourier(String name, String phone) {
        this.courierName = name;
        this.courierPhone = phone;
    }
    public void setNotes(String notes) { this.notes = notes; }
}
