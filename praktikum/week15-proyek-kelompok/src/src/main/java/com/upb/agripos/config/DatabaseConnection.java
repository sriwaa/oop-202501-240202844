package main.java.com.upb.agripos.config;

public class DatabaseConnection {
    // 1. Static variable untuk menyimpan satu-satunya instance
    private static DatabaseConnection instance;

    // 2. Private constructor agar tidak bisa di-instansiasi dari luar
    private DatabaseConnection() {
        System.out.println("Koneksi Database berhasil dibuat (Singleton).");
    }

    // 3. Static method untuk mengakses instance global
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    public String getStatus() {
        return "Terhubung ke Database Agri-POS";
    }
}