package main.java.com.upb.agripos.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String role; // Field Baru

    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; } // Getter ini dipanggil Controller
}