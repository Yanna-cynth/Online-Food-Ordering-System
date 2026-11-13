package foodordering.model;

public abstract class User {
    private final int id;
    private String name;
    private final String username;
    private String password;
    private String phone;

    protected User(int id, String name, String username, String password, String phone) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPhone() { return phone; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public boolean checkPassword(String candidate) { return password.equals(candidate); }
    public void changePassword(String newPassword) { this.password = newPassword; }
    public abstract String getRole();
}
