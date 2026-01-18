# Laporan Praktikum Minggu 11
Topik: Data Access Object (DAO) dan CRUD Database dengan JDBC

## Identitas
- Nama  : [SRI WAHYUNINGSIH]
- NIM   : [240202844]
- Kelas : [3IKRA]

---

## Tujuan
1. Menjelaskan konsep Data Access Object (DAO) dalam pengembangan aplikasi OOP.
2. Menghubungkan aplikasi Java dengan basis data menggunakan JDBC.
3. Mengimplementasikan operasi CRUD (Create, Read, Update, Delete) secara lengkap.
4. Mengintegrasikan DAO dengan class aplikasi OOP sesuai prinsip desain yang baik.

---

## Dasar Teori
1. DAO (Data Access Object): Pola desain yang memisahkan logika akses data dari logika bisnis aplikasi.
2. JDBC (Java Database Connectivity): Digunakan untuk menghubungkan aplikasi Java dengan basis data relasional seperti PostgreSQL.
3. PreparedStatement: Komponen JDBC yang digunakan untuk menjalankan query SQL secara aman dan efisien.
4. Enkapsulasi Data: Menggunakan class Model (Product) untuk membungkus data tabel menjadi objek Java.

---

## Langkah Praktikum
1. Setup Database: Membuat database agripos dan tabel products melalui terminal PostgreSQL (psql).
2. Coding Model & DAO: Membuat class Product, interface ProductDAO, dan implementasi ProductDAOImpl menggunakan JDBC.
3. Setup Library: Menambahkan PostgreSQL JDBC Driver ke dalam proyek Java.
4. Eksekusi: Menjalankan MainDAOTest.java untuk melakukan operasi Insert, Update, Read, dan Delete secara berurutan.
5. Verifikasi: Melakukan pengecekan data langsung pada database menggunakan perintah SQL SELECT * FROM products;.
6. Commit message: week11-dao-database
---

## Kode Program
1. Kode Program Product.java
   package com.upb.agripos.model;

public class Product {

    private String code;
    private String name;
    private double price;
    private int stock;

    public Product(String code, String name, double price, int stock) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
}


2. Kode Program ProductDAO.java
   package com.upb.agripos.dao;

import java.util.List;
import com.upb.agripos.model.Product;

public interface ProductDAO {
    void insert(Product product) throws Exception;
    Product findByCode(String code) throws Exception;
    List<Product> findAll() throws Exception;
    void update(Product product) throws Exception;
    void delete(String code) throws Exception;
}


3. Kode Program ProductDAOImpl.java
   package com.upb.agripos.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.upb.agripos.model.Product;

public class ProductDAOImpl implements ProductDAO {

    private final Connection connection;

    public ProductDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Product p) throws Exception {
        String sql = "INSERT INTO products(code, name, price, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getCode());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getStock());
            ps.executeUpdate();
        }
    }

    @Override
    public Product findByCode(String code) throws Exception {
        String sql = "SELECT * FROM products WHERE code = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
            }
        }
        return null;
    }

    @Override
    public List<Product> findAll() throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Product(
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                ));
            }
        }
        return list;
    }

    @Override
    public void update(Product p) throws Exception {
        String sql = "UPDATE products SET name=?, price=?, stock=? WHERE code=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getStock());
            ps.setString(4, p.getCode());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(String code) throws Exception {
        String sql = "DELETE FROM products WHERE code=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.executeUpdate();
        }
    }
}


4. Kode Program MainDAOTest.java
   package com.upb.agripos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.model.Product;

public class MainDAOTest {

    public static void main(String[] args) throws Exception {

        Class.forName("org.postgresql.Driver");

        Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/agripos",
                "postgres",
                "225522" 
        );

        ProductDAO dao = new ProductDAOImpl(conn);

        System.out.println("=== MEMULAI PENGUJIAN CRUD DAO ===");

        // 1. CREATE (Insert)
        System.out.println("\n[1] Menjalankan Operation: CREATE");
        Product newProduct = new Product("P01", "Pupuk Organik", 25000, 10);
        dao.insert(newProduct);
        System.out.println(">>> Berhasil menambahkan: " + newProduct.getName());

        // 2. UPDATE
        System.out.println("\n[2] Menjalankan Operation: UPDATE");
        Product updatedProduct = new Product("P01", "Pupuk Organik Premium", 30000, 8);
        dao.update(updatedProduct);
        System.out.println(">>> Data P01 diperbarui menjadi: " + updatedProduct.getName());

        // 3. READ BY CODE (Find)
        System.out.println("\n[3] Menjalankan Operation: READ BY CODE");
        Product p = dao.findByCode("P01");
        if (p != null) {
            System.out.println(">>> Ditemukan -> Nama: " + p.getName() + ", Harga: " + p.getPrice() + ", Stok: " + p.getStock());
        }

        // 4. READ ALL (List)
        System.out.println("\n[4] Menjalankan Operation: READ ALL");
        List<Product> products = dao.findAll();
        System.out.println(">>> Daftar seluruh produk di database:");
        for (Product prod : products) {
            System.out.println("    - " + prod.getCode() + " | " + prod.getName() + " | Rp" + prod.getPrice());
        }

        // 5. DELETE
        System.out.println("\n[5] Menjalankan Operation: DELETE");
        dao.delete("P01");
        System.out.println(">>> Produk P01 berhasil dihapus.");

        System.out.println("\n=== SEMUA OPERASI BERHASIL DIUJI ===");

        conn.close();
    }
}


---

## Hasil Eksekusi
![alt text](<week11_hasil eksekusi.png>)

---

## Analisis
- Cara Kerja: Kode berjalan dengan menghubungkan Java ke PostgreSQL via DriverManager. DAO bertindak sebagai jembatan; saat insert() dipanggil, ProductDAOImpl mengirim perintah SQL ke database.

- Perbedaan: Berbeda dengan minggu sebelumnya yang mungkin menggunakan array/list di memori, minggu ini data bersifat permanen (persistent) karena disimpan di database.

- Kendala & Solusi:
Kendala: Error No suitable driver found.
Solusi: Mengunduh .jar PostgreSQL JDBC Driver dan menambahkannya ke Referenced Libraries di VS Code.
Kendala: Lupa password database.
Solusi: Melakukan instalasi ulang PostgreSQL dan mencatat password baru dengan teliti.

---

## Kesimpulan
Dengan pola DAO, kode program menjadi lebih rapi dan terstruktur karena logika database terpisah dari logika utama. Penggunaan JDBC memungkinkan aplikasi Java untuk memanipulasi data secara dinamis pada sistem database profesional seperti PostgreSQL.

---
