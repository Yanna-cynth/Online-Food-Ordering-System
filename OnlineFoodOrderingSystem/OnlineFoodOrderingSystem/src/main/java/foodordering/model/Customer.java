package foodordering.model;

public class Customer extends User {
    private String address;

    public Customer(int id, String name, String username, String password, String phone, String address) {
        super(id, name, username, password, phone);
        this.address = address;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    @Override public String getRole() { return "CUSTOMER"; }
}
