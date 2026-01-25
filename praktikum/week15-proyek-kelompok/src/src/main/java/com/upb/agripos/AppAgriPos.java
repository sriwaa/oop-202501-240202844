package main.java.com.upb.agripos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import main.java.com.upb.agripos.dao.*;     // Import semua DAO (Termasuk Member)
import main.java.com.upb.agripos.service.*; // Import Service
import main.java.com.upb.agripos.view.*;    // Import View
import main.java.com.upb.agripos.controller.MainController;

import java.sql.Connection;
import java.sql.DriverManager;

public class AppAgriPos extends Application {
    private Stage primaryStage;
    private Connection conn;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        // 1. Anti-Stuck: Tangkap semua error tak terduga agar muncul di layar
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> showError("System Error", e.getMessage()));

        try {
            // 2. Koneksi Database
            // Sesuaikan user/password jika beda
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/agripos", "postgres", "111123");
            
            // 3. Masuk ke Layar Login
            showLoginScreen();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Gagal Koneksi Database", "Cek apakah PostgreSQL sudah jalan.\nError: " + e.getMessage());
        }
    }

    private void showLoginScreen() {
        LoginView loginView = new LoginView();
        
        // Siapkan sistem Login
        UserRepository userRepo = new SqlUserRepository(conn);
        AuthService authService = new AuthService(userRepo);

        loginView.getBtnLogin().setOnAction(e -> {
            try {
                String u = loginView.getUsername();
                String p = loginView.getPassword();

                if (authService.login(u, p)) {
                    System.out.println("Login Sukses! Memuat Dashboard...");
                    showMainScreen(); // <--- Masuk Dashboard
                } else {
                    showError("Login Gagal", "Username atau Password salah!");
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
                showError("CRASH SAAT LOGIN", "Detail: " + ex.toString());
            }
        });

        primaryStage.setScene(new Scene(loginView.asParent(), 300, 250));
        primaryStage.setTitle("Login Agri-POS");
        primaryStage.show();
    }

    private void showMainScreen() {
        try {
            // === BAGIAN PENTING: WIRING SEMUA KOMPONEN ===
            
            // 1. Siapkan Repository (Akses Database)
            ProductRepository prodRepo = new SqlProductRepository(conn);
            SqlTransactionRepository transRepo = new SqlTransactionRepository(conn);
            SqlMemberRepository memberRepo = new SqlMemberRepository(conn); // <--- BARU (Untuk Tab Member)
            
            // 2. Siapkan Service (Logika Bisnis)
            ProductService prodService = new ProductService(prodRepo);
            CartService cartService = new CartService();
            
            // 3. Siapkan View (Tampilan)
            MainView mainView = new MainView();

            // 4. Siapkan Controller (Otak Aplikasi)
            // Perhatikan urutan parameter harus sama dengan Constructor di MainController
            new MainController(prodService, cartService, transRepo, memberRepo, mainView);

            // 5. Tampilkan
            Scene scene = new Scene(mainView.asParent(), 950, 650); // Ukuran sedikit diperlebar
            primaryStage.setScene(scene);
            primaryStage.setTitle("Dashboard Agri-POS - " + AuthService.getCurrentUser().getUsername());
            primaryStage.centerOnScreen();
            
        } catch (Throwable e) {
            e.printStackTrace();
            showError("Gagal Memuat Dashboard", "Terjadi kesalahan inisialisasi:\n" + e.getMessage());
        }
    }
    
    // Helper untuk menampilkan kotak pesan error
    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}