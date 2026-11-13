package foodordering.model;

public class Admin extends User {
    public Admin(int id, String name, String username, String password, String phone) {
        super(id, name, username, password, phone);
    }
    @Override public String getRole() { return "ADMIN"; }
}
